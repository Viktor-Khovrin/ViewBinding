package com.example.filmsSearch.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.filmsSearch.App
import com.example.filmsSearch.data.Entity.Film
import com.example.filmsSearch.domain.Interactor
import javax.inject.Inject

class FavoritesFragmentViewModel : ViewModel() {

    @Inject
    lateinit var interactor: Interactor
    lateinit var filmsListLiveData: LiveData<List<Film>>
    fun init() {
        App.instance.dagger.inject(this)
        getFilmsFromDB()
    }

    private fun getFilmsFromDB() {
            filmsListLiveData = interactor.getFilmsFromDB()
        }

    interface ApiCallback {
        fun onSuccess()
        fun onFailure()
    }

}