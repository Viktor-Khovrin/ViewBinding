package com.example.filmsSearch.utils

import com.example.db_module.entity.Film


object Converter {
    fun convertApiListToDtoList(list: List<com.example.remote_module.entity.Doc>?): List<Film> {
        val result = mutableListOf<Film>()
        list?.forEach {
            result.add(
                Film(
                    id = it.id,
                    title = it.name,
                    poster = it.poster.url,
                    description = it.description,
                    rating = it.rating.kp,
                    isInFavorites = false
                )
            )
        }
        return result
    }
}