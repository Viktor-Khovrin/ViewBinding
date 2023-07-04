package com.example.filmsSearch.view.viewmodel

import androidx.lifecycle.ViewModel
import com.example.filmsSearch.App
import com.example.filmsSearch.data.Entity.Film
import com.example.filmsSearch.domain.Interactor
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoritesFragmentViewModel : ViewModel() {

    @Inject
    lateinit var interactor: Interactor
    lateinit var filmsListFlow: Flow<List<Film>>
    init {
        App.instance.dagger.inject(this)
        getFilmsFromDB()
    }

    private fun getFilmsFromDB() {
        filmsListFlow = interactor.getFilmsFromDB()
        }

    interface ApiCallback {
        fun onSuccess()
        fun onFailure()
    }

}