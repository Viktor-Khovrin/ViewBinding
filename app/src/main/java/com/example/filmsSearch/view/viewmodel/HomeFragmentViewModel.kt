package com.example.filmsSearch.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmsSearch.App
import com.example.filmsSearch.data.Entity.Film
import com.example.filmsSearch.domain.Interactor
import java.util.concurrent.Executors
import javax.inject.Inject

class HomeFragmentViewModel: ViewModel() {
    val filmsListLiveData: MutableLiveData<List<Film>> = MutableLiveData()
    var isInitialized = false
    private val diffTimeout = 10*60*1000
    //Инициализируем интерактор
    @Inject
    lateinit var interactor: Interactor
    fun init() {
        App.instance.dagger.inject(this)
        getFilms()
    }
    fun getFilms(){
        if (interactor.getCurrentQueryTime()+diffTimeout <= System.currentTimeMillis()) {
                interactor.getFilmsFromApi(1, object : ApiCallback {
                    override fun onSuccess(films: List<Film>) {
                        filmsListLiveData.postValue(films)
                        interactor.setCurrentQueryTime()
                    }

                    override fun onFailure() {
                        Executors.newSingleThreadExecutor().execute {
                            filmsListLiveData.postValue(interactor.getFilmsFromDB())
                        }
                    }
                })
        }
        else {
            Executors.newSingleThreadExecutor().execute {
                filmsListLiveData.postValue(interactor.getFilmsFromDB())
            }
        }
    }

    interface ApiCallback {
        fun onSuccess(films: List<Film>)
        fun onFailure()
    }

}