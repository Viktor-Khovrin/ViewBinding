package com.example.filmsSearch.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmsSearch.domain.Film
import org.koin.core.component.KoinComponent

class FavoritesFragmentViewModel:ViewModel(), KoinComponent {
    val filmsListLiveDataFavorite: MutableLiveData<List<Film>> =MutableLiveData()
//            filmsListLiveDataFavorite: MutableLiveData<List<Film>> = MutableLiveData()
//    private val interactor: Interactor by inject()
//    private var interactor: Interactor = App.instance.interactor
//    init {
//        interactor.getFilmsFromApi(1, object : HomeFragmentViewModel.ApiCallback {
//            override fun onSuccess(films: List<Film>) {
//        filmsListLiveDataFavorite.postValue(filmsListLiveData.value!!.filter { it.isInFavorites })
//            }

//            override fun onFailure() {
//            }
//        })
//    }
}