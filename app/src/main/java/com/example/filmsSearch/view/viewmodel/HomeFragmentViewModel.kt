package com.example.filmsSearch.view.viewmodel

import androidx.lifecycle.ViewModel
import com.example.db_module.entity.Film
import com.example.filmsSearch.App
import com.example.filmsSearch.domain.Interactor
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

class HomeFragmentViewModel: ViewModel() {
    val showProgressBar: BehaviorSubject<Boolean>
    lateinit var filmsListFlow: Observable<List<Film>>
    private val diffTimeout = 10*60*1000
    //Initializing interactor
    @Inject
    lateinit var interactor: Interactor
    init {
//        Thread.dumpStack()
        App.instance.dagger.inject(this)
        showProgressBar = interactor.progressBarStatus
        getFilms()
    }
    suspend fun getOneFilmFromDB(filmId: Int): Film = interactor.getOneFilmFromDB(filmId)

    fun getFilms(page: Int = 1) {
        if (interactor.getCurrentQueryTime()+diffTimeout <= System.currentTimeMillis()) {
//            filmsListFlow = interactor.getFilmsFromDB()
            interactor.getFilmsFromApi(page)
            filmsListFlow = interactor.getFilmsFromDB()
        }else{filmsListFlow = interactor.getFilmsFromDB()}
    }

    fun getSearchResult(page: Int=1, search: String) = interactor.getSearchResultFromApi(page, search)

    interface ApiCallback {
        fun onSuccess()
        fun onFailure()
    }

}