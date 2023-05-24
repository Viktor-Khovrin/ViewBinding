package com.example.filmsSearch.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmsSearch.domain.Film
import com.example.filmsSearch.domain.Interactor
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FavoritesFragmentViewModel:ViewModel(), KoinComponent {
    val filmsListLiveDataFavorite: MutableLiveData<List<Film>> = MutableLiveData()
    private val flDB = HomeFragmentViewModel().filmsListLiveData
    private val interactor: Interactor by inject()
//    private var interactor: Interactor = App.instance.interactor
    init {
        interactor.getFilmsFromApi(1, object : HomeFragmentViewModel.ApiCallback {
            override fun onSuccess(films: List<Film>) {
        filmsListLiveDataFavorite.postValue(films)
            }

            override fun onFailure() {
            }
        })
    }
}