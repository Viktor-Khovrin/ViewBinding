package com.example.filmsSearch.domain

import com.example.db_module.MainRepository
import com.example.db_module.entity.Film
import com.example.filmsSearch.App
import com.example.filmsSearch.R
import com.example.filmsSearch.data.sp.PreferenceProvider
import com.example.filmsSearch.utils.ApiKey
import com.example.filmsSearch.utils.Converter
import com.example.remote_module.TmdbApi
import com.example.remote_module.entity.TmdbResultsDto
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Interactor(private val repo: MainRepository,
                 private val retrofitService: TmdbApi,
                 private val preferences: PreferenceProvider) {
    //Values for API
    var top250 : String? = null
    var ticketsOnSale: String? = null
    var genres: String? = null

    val context = App.instance
    var progressBarStatus: BehaviorSubject<Boolean> = BehaviorSubject.create()

    fun getFilmsFromApi(page: Int) {
        initValues()
        progressBarStatus.onNext(true)
        retrofitService.getFilms(ApiKey.API,
                                page,
                                top250,
                                ticketsOnSale,
                                genres)
            .enqueue(object :
                Callback<TmdbResultsDto> {
                override fun onResponse(call: Call<TmdbResultsDto>, response: Response<TmdbResultsDto>) {
                    val list = Converter.convertApiListToDtoList(response.body()?.docs)
                    Completable.fromSingle<List<Film>> {
                        repo.putToDb(list)
                    }
                        .subscribeOn(Schedulers.io())
                        .subscribe()
                    setCurrentQueryTime()
                    progressBarStatus.onNext(false)
                }

                override fun onFailure(call: Call<TmdbResultsDto>, t: Throwable) {
                    Completable.fromSingle<List<Film>> {
                        repo.getAllFromDB()
                    }
                        .subscribeOn(Schedulers.io())
                        .subscribe()
                    progressBarStatus.onNext(false)
                }
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