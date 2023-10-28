package com.example.filmsSearch.view.viewmodel

import androidx.lifecycle.ViewModel
import com.example.db_module.entity.Film
import com.example.filmsSearch.App
import com.example.filmsSearch.domain.Interactor
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class FavoritesFragmentViewModel : ViewModel() {

    @Inject
    lateinit var interactor: Interactor
    lateinit var filmsListFlow: Observable<List<Film>>
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