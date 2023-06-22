package com.example.filmsSearch.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmsSearch.App
import com.example.filmsSearch.data.Entity.Film
import com.example.filmsSearch.domain.Interactor
import javax.inject.Inject

class HomeFragmentViewModel: ViewModel() {
    val showProgressBar: MutableLiveData<Boolean> = MutableLiveData()
    lateinit var filmsListLiveData: LiveData<List<Film>>
    private val diffTimeout = 10*60*1000
    //Инициализируем интерактор
    @Inject
    lateinit var interactor: Interactor
    fun init() {
        App.instance.dagger.inject(this)
        filmsListLiveData = interactor.getFilmsFromDB()
        getFilms()
    }
    fun getFilms(){
        if (interactor.getCurrentQueryTime()+diffTimeout <= System.currentTimeMillis()) {
            showProgressBar.postValue(true)
                interactor.getFilmsFromApi(1, object : ApiCallback {
                    override fun onSuccess() {
                        showProgressBar.postValue(false)
                        interactor.setCurrentQueryTime()
                    }

                    override fun onFailure() {
                        showProgressBar.postValue(false)
                    }
                })
        }
//        else {
//            Executors.newSingleThreadExecutor().execute {
//                filmsListLiveData.postValue(interactor.getFilmsFromDB())
//            }
//        }
    }

    interface ApiCallback {
        fun onSuccess()
        fun onFailure()
    }

}