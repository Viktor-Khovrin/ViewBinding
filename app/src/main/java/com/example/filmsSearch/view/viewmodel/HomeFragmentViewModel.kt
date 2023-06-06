package com.example.filmsSearch.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmsSearch.App
import com.example.filmsSearch.domain.Film
import com.example.filmsSearch.domain.Interactor
import javax.inject.Inject

class HomeFragmentViewModel: ViewModel() {
    val filmsListLiveData: MutableLiveData<List<Film>> = MutableLiveData()
    var isInitialized = false
    //Инициализируем интерактор
    @Inject
    lateinit var interactor: Interactor
    fun init() {
        App.instance.dagger.inject(this)
        getFilms()
    }
    fun getFilms(){
        if (!isInitialized) {
            interactor.getFilmsFromApi(1, object : ApiCallback {
                override fun onSuccess(films: List<Film>) {
                    filmsListLiveData.postValue(films)
                    isInitialized = true
                }

                override fun onFailure() {
                }
            })
        }
    }

    interface ApiCallback {
        fun onSuccess(films: List<Film>)
        fun onFailure()
    }

}