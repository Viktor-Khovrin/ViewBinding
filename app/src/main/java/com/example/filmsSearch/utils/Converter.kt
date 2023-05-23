package com.example.filmsSearch.utils

import com.example.filmsSearch.data.Entity.Doc
import com.example.filmsSearch.domain.Film

object Converter {
    fun convertApiListToDtoList(list: List<Doc>?): List<Film> {
        val result = mutableListOf<Film>()
        list?.forEach {
            result.add(Film(
                title = it.name,
                poster = it.poster.url,
                description = it.description,
                rating = it.rating.kp,
                isInFavorites = false
            ))
        }
        return result
    }
}