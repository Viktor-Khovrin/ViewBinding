package com.example.filmsSearch.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmsSearch.App
import com.example.filmsSearch.data.Entity.Film
import com.example.filmsSearch.domain.Interactor
import com.example.filmsSearch.utils.MessageEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject


class DetailsFragmentViewModel: ViewModel() {
    var filmLiveData: MutableLiveData<Film> = MutableLiveData()
    @Inject
    lateinit var interactor: Interactor
    fun init() {
        App.instance.dagger.inject(this)
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
//        getOneFilmFromDB(filmLiveData.value!!.id)
    }

    private fun getOneFilmFromDB(filmId: Int){
        filmLiveData.value = interactor.getOneFilmFromDB(filmId)
    }

    @Subscribe
    fun onMessageEvent(event: MessageEvent) {
//        Toast.makeText(interactor.context, event.message, Toast.LENGTH_SHORT).show()
        putOneFilmToDB(filmLiveData.value as Film)
    }

    fun putOneFilmToDB(film: Film){
        interactor.updateFilmInDb(film)
    }

    override fun onCleared() {
        EventBus.getDefault().unregister(this)
        super.onCleared()
    }
}