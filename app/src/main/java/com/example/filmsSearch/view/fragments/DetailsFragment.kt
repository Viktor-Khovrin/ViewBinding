package com.example.filmsSearch.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.filmsSearch.R
import com.example.filmsSearch.databinding.FragmentDetailsBinding
import com.example.filmsSearch.domain.Film

class DetailsFragment : Fragment() {
    private var bindingDetails: FragmentDetailsBinding?=null
    private val binding get() = bindingDetails!!
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
        setDetailedContent()
    }

    private fun setDetailedContent() {
        val film = arguments?.get(FILM_FIELD_NAME) as Film
        //details_toolbar.title = film.title
        binding.detailsToolbar.title = film.title
//        //details_poster.setImageResource(film.poster)
//        binding.detailsPoster.setImageResource(film.poster)
        Glide.with(this)
            .load(film.poster)
                //ApiConstants.IMAGES_URL + "w780" +
            .centerCrop()
            .into(binding.detailsPoster)
//        //details_description.text = film.description
        binding.detailsDescription.text =film.description

        //details_fab_favorites.setImageResource(
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
        }

        binding.detailsFabShare.setOnClickListener{
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, "Checkout this film: ${film.title} \n\n ${film.description}")
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, "Share to:"))
        }
    }
    companion object{
        private const val FILM_FIELD_NAME = "film"
    }

    override fun onDestroyView() {
        bindingDetails = null
        super.onDestroyView()
    }
}