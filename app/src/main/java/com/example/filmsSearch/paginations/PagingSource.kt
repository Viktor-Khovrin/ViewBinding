package com.example.filmsSearch.paginations

import android.graphics.Movie
import com.example.db_module.entity.Film

class PagingSource(): PagingSource<Int, Movie>() {

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Film> {
            return try {
                LoadResult.Page()
            } catch (e: Exception) {
                LoadResult.Error(e)
            }
        }

        override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
            return null
        }

}