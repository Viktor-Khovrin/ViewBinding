package com.example.filmsSearch.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmsSearch.App
import com.example.filmsSearch.domain.Film
import com.example.filmsSearch.domain.Interactor

class FavoritesFragmentViewModel:ViewModel() {
        val filmsListLiveData: MutableLiveData<List<Film>> = MutableLiveData()
        //Инициализируем интерактор
        private var interactor: Interactor = App.instance.interactor
        init {
            val films = interactor.getFilmsDB()
            filmsListLiveData.postValue(films.filter {it.isInFavorites})
        }
}