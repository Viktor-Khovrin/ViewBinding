package com.example.filmsSearch.view.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.db_module.entity.Film
import com.example.filmsSearch.App
import com.example.filmsSearch.domain.Interactor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class DetailsFragmentViewModel: ViewModel() {
    var filmLiveData= MutableLiveData<Film>()
    @Inject
    lateinit var interactor: Interactor
    private val observer = Observer<Film> { putOneFilmToDB(filmLiveData.value as Film) }
    init {
        App.instance.dagger.inject(this)
//        if (!EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().register(this)
//        }
        filmLiveData.observeForever(observer)
    }

    suspend fun loadWallpaper(url: String): Bitmap {
        return suspendCoroutine {
            val url = URL(url)
            val bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
            it.resume(bitmap)
        }
    }

    fun putOneFilmToDB(film: Film){
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            interactor.updateFilmInDb(film)
        }
    }

    override fun onCleared() {
        filmLiveData.removeObserver(observer)
//        EventBus.getDefault().unregister(this)
        super.onCleared()
    }
}