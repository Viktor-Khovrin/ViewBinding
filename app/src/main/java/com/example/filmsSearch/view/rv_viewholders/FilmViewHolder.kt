package com.example.filmsSearch.view.rv_viewholders

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.filmsSearch.data.Entity.Film
import com.example.filmsSearch.databinding.FilmItemBinding

//import kotlinx.android.synthetic.main.film_item.view.*

class FilmViewHolder(val binding: FilmItemBinding) : RecyclerView.ViewHolder(binding.root) {
    private val title = binding.title
    private val poster = binding.poster
    private val description = binding.description
    private val ratingDonut = binding.ratingDonut
    fun onBind(film: Film){
        title.text = film.title
        Glide.with(itemView)
            .load(film.poster)
                //ApiConstants.IMAGES_URL + "w342" +
            .centerCrop()
            .into(poster)
        description.text = film.description
        ratingDonut.setProgress((film.rating * 10).toInt())
    }
}