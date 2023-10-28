package com.example.filmsSearch.view.fragments

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.db_module.entity.Film
import com.example.filmsSearch.R
import com.example.filmsSearch.databinding.FragmentDetailsBinding
import com.example.filmsSearch.view.viewmodel.DetailsFragmentViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DetailsFragment : Fragment() {
    private var bindingDetails: FragmentDetailsBinding?=null
    private val binding get() = bindingDetails!!
    private lateinit var film: Film
    private val viewModel by lazy {
        ViewModelProvider(requireActivity()).get(DetailsFragmentViewModel::class.java)
    }
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingDetails = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        @Suppress("DEPRECATION")
        film = arguments?.get(FILM_FIELD_NAME) as Film
        viewModel.filmLiveData.value = film
        viewModel.filmLiveData.observe(viewLifecycleOwner, Observer<Film> {film = it})
        setDetailedContent()
        binding .detailsFabDownloadWp.setOnClickListener {
            performAsyncLoadOfPoster()
        }

    }

    private fun setDetailedContent() {
        binding.detailsToolbar.title = film.title
        Glide.with(this)
            .load(film.poster)
            .centerCrop()
            .into(binding.detailsPoster)
        binding.detailsDescription.text =film.description

        binding.detailsFabFavorites.setImageResource(
            if (!film.isInFavorites) R.drawable.ic_baseline_favorite_border_24
            else R.drawable.ic_baseline_favorite_24
        )

        binding.detailsFabFavorites.setOnClickListener{
            if (film.isInFavorites){
                binding.detailsFabFavorites.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                film.isInFavorites = false

            }else{
                binding.detailsFabFavorites.setImageResource(R.drawable.ic_baseline_favorite_24)
                film.isInFavorites = true
            }
            viewModel.filmLiveData.postValue(film)
//            EventBus.getDefault().post(MessageEvent(film.id))
        }

        binding.detailsFabShare.setOnClickListener{
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, "Checkout this film: ${film.title} \n\n ${film.description}")
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, "Share to:"))
        }
    }
    //Having rights?
    private fun checkPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        return result == PackageManager.PERMISSION_GRANTED
    }

    //Requesting permissions
    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            1
        )
    }

    private fun saveToGallery(bitmap: Bitmap) {
        //Проверяем версию системы
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            //Создаем объект для передачи данных
            val contentValues = ContentValues().apply {
                //Составляем информацию для файла (имя, тип, дата создания, куда сохранять и т.д.)
                put(MediaStore.Images.Media.TITLE, film.title.handleSingleQuote())
                put(
                    MediaStore.Images.Media.DISPLAY_NAME,
                    film.title.handleSingleQuote()
                )
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(
                    MediaStore.Images.Media.DATE_ADDED,
                    System.currentTimeMillis() / 1000
                )
                put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/FilmsSearchApp")
            }
            //Получаем ссылку на объект Content resolver, который помогает передавать информацию из приложения вовне
            val contentResolver = requireActivity().contentResolver
            val uri = contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            //Открываем канал для записи на диск
            val outputStream = contentResolver.openOutputStream(uri!!)
            //Передаем нашу картинку, может сделать компрессию
            if (outputStream != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            }
            //Закрываем поток
            outputStream?.close()
        } else {
            //То же, но для более старых версий ОС
            @Suppress("DEPRECATION")
            MediaStore.Images.Media.insertImage(
                requireActivity().contentResolver,
                bitmap,
                film.title.handleSingleQuote(),
                film.description.handleSingleQuote()
            )
        }
    }

    private fun performAsyncLoadOfPoster() {
        //Проверяем есть ли разрешение
        if (!checkPermission()) {
            //Если нет, то запрашиваем и выходим из метода
            requestPermission()
            return
        }
        //Создаем родительский скоуп с диспатчером Main потока, так как будем взаимодействовать с UI
        MainScope().launch {
            if (!film.poster.isNullOrBlank()) {
                //Включаем Прогресс-бар
                binding.progressBar.isVisible = true
                //Создаем через async, так как нам нужен результат от работы, то есть Bitmap
                val job = scope.async {
                    viewModel.loadWallpaper(film.poster!!)
                }
                //Сохраняем в галерею, как только файл загрузится
                saveToGallery(job.await())
                //Выводим снекбар с кнопкой перейти в галерею
                Snackbar.make(
                    binding.root,
                    R.string.downloaded_to_gallery,
                    Snackbar.LENGTH_LONG
                )
                    .setAction(R.string.open) {
                        val intent = Intent()
                        intent.action = Intent.ACTION_VIEW
                        intent.type = "image/*"
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    }
                    .show()
            }
            //Отключаем Прогресс-бар
            binding.progressBar.isVisible = false
        }
    }

    private fun String.handleSingleQuote(): String {
        return this.replace("'", "")
    }

    override fun onDestroyView() {
        bindingDetails = null
        super.onDestroyView()
    }

    companion object{
        private const val FILM_FIELD_NAME = "film"
    }
}