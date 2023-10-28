package com.example.filmsSearch.domain

import android.annotation.SuppressLint
import com.example.db_module.MainRepository
import com.example.db_module.entity.Film
import com.example.filmsSearch.App
import com.example.filmsSearch.R
import com.example.filmsSearch.data.sp.PreferenceProvider
import com.example.filmsSearch.utils.ApiKey
import com.example.filmsSearch.utils.Converter
import com.example.remote_module.TmdbApi
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject

class Interactor(private val repo: MainRepository,
                 private val retrofitService: TmdbApi,
                 private val preferences: PreferenceProvider) {
    //Values for API
    var top250 : String? = null
    var ticketsOnSale: String? = null
    var genres: String? = null

    val context = App.instance
    var progressBarStatus: BehaviorSubject<Boolean> = BehaviorSubject.create()

    @SuppressLint("CheckResult")
    fun getFilmsFromApi(page: Int) {
        initValues()
        progressBarStatus.onNext(true)
        retrofitService.getFilms(ApiKey.API,
                                page,
                                top250,
                                ticketsOnSale,
                                genres)
            .subscribeOn(Schedulers.io())
            .map {
                Converter.convertApiListToDtoList(it.docs)
            }
            .subscribeBy(
                onError = {progressBarStatus.onNext(false)},
                onNext = {
                    progressBarStatus.onNext(false)
                    repo.putToDb(it)
                    setCurrentQueryTime()
                }
            )
    }

    fun getFilmsFromDB(): Observable<List<Film>> = repo.getAllFromDB()

    fun getOneFilmFromDB(id: Int): Film = repo.getById(id)

    fun updateFilmInDb(film: Film) {
        repo.updateInDb(film)
    }

    //Save preferences
    fun saveDefaultCategoryToPreferences(category: String) {
        preferences.saveDefaultCategory(category)
    }
    //Get preferences
    fun getDefaultCategoryFromPreferences() = preferences.getDefaultCategory()

    fun setCurrentQueryTime() = preferences.setQueryTime()

    fun setWrongCurrentQueryTime() = preferences.setWrongQueryTime()

    fun getCurrentQueryTime() = preferences.getQueryTime()

    fun getSearchResultFromApi(page: Int, search: String): Observable<List<Film>> = retrofitService
        .getFilmsFromSearch(ApiKey.API, page, top250, ticketsOnSale, genres, search)
        .map {
            Converter.convertApiListToDtoList(it.docs)
        }

    fun initValues(){
        when (getDefaultCategoryFromPreferences()){
            context.getString(R.string.radio_button_popular) -> { }
            context.getString(R.string.radio_button_top_rated) -> {
                top250 = "!null"
            }
            context.getString(R.string.radio_button_playing) ->{
                ticketsOnSale = "true"
            }
            context.getString(R.string.radio_button_genres) ->{
                genres = "комедия"
            }
        }
    }
}