package com.example.filmsSearch.view.viewmodel

import androidx.lifecycle.ViewModel
import com.example.filmsSearch.App
import com.example.filmsSearch.data.Entity.Film
import com.example.filmsSearch.domain.Interactor
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeFragmentViewModel: ViewModel() {
    val showProgressBar: Channel<Boolean>
    val filmsListFlow: Flow<List<Film>>
    private val diffTimeout = 10*60*1000
    //Initializing interactor
    @Inject
    lateinit var interactor: Interactor
    init {
//        Thread.dumpStack()
        App.instance.dagger.inject(this)
        showProgressBar = interactor.progressBarStatus
        filmsListFlow = interactor.getFilmsFromDB()
        getFilms()
    }
    fun getFilms(){
        if (interactor.getCurrentQueryTime()+diffTimeout <= System.currentTimeMillis()) {
            interactor.getFilmsFromApi(1)
        }
    }

    interface ApiCallback {
        fun onSuccess()
        fun onFailure()
    }

}