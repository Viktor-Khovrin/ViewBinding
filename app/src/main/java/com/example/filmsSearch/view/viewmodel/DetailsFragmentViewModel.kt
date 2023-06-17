package com.example.filmsSearch.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.filmsSearch.App
import com.example.filmsSearch.data.Entity.Film
import com.example.filmsSearch.domain.Interactor
import com.example.filmsSearch.utils.MessageEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject


class DetailsFragmentViewModel: ViewModel() {
    var filmLiveData= MutableLiveData<Film>()
    @Inject
    lateinit var interactor: Interactor
    private val observer = Observer<Film> { putOneFilmToDB(filmLiveData.value as Film) }
    fun init() {
        App.instance.dagger.inject(this)
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
        filmLiveData.observeForever(observer)
//        getOneFilmFromDB(filmLiveData.value!!.id)
    }

    private fun getOneFilmFromDB(filmId: Int){
        filmLiveData.value = interactor.getOneFilmFromDB(filmId)
    }

    @Subscribe
    fun onMessageEvent(event: MessageEvent) {
//        putOneFilmToDB(filmLiveData.value as Film)
    }

    fun putOneFilmToDB(film: Film){
        interactor.updateFilmInDb(film)
    }

    override fun onCleared() {
        filmLiveData.removeObserver(observer)
        EventBus.getDefault().unregister(this)
        super.onCleared()
    }
}