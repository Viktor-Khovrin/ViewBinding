package com.example.filmsSearch.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmsSearch.domain.Film
import com.example.filmsSearch.domain.Interactor
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SharedViewModel:ViewModel(), KoinComponent {
    val filmsListLiveData: MutableLiveData<List<Film>> = MutableLiveData()
    private var isInitialized = false
    private val interactor: Interactor by inject()
    fun init() {
        if (!isInitialized) {
            interactor.getFilmsFromApi(1, object : ApiCallback {
                override fun onSuccess(films: List<Film>) {
                    filmsListLiveData.postValue(films)
                    isInitialized = true
                    //                putData(films)
                }

                override fun onFailure() {
                }
            })
        }
    }
    fun putData(value:List<Film>){
        filmsListLiveData.postValue(value)
    }

    fun getData():MutableLiveData<List<Film>>{
        return filmsListLiveData
    }

    interface ApiCallback {
        fun onSuccess(films: List<Film>)
        fun onFailure()
    }

}