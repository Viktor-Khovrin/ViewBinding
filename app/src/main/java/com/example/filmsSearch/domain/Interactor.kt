package com.example.filmsSearch.domain

import com.example.filmsSearch.App
import com.example.filmsSearch.R
import com.example.filmsSearch.data.Entity.Film
import com.example.filmsSearch.data.Entity.TmdbResultsDto
import com.example.filmsSearch.data.MainRepository
import com.example.filmsSearch.data.TmdbApi
import com.example.filmsSearch.data.sp.PreferenceProvider
import com.example.filmsSearch.utils.ApiKey
import com.example.filmsSearch.utils.Converter
import com.example.filmsSearch.view.viewmodel.HomeFragmentViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Interactor(private val repo: MainRepository,
                 private val retrofitService: TmdbApi,
                 private val preferences: PreferenceProvider) {
    val context = App.instance

    fun getFilmsFromApi(page: Int, callback: HomeFragmentViewModel.ApiCallback) {
        var top250 : String? = null
        var ticketsOnSale: String? = null
        var genres: String? = null
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
        retrofitService.getFilms(ApiKey.API,
                                page,
                                top250,
                                ticketsOnSale,
                                genres)
            .enqueue(object :
                Callback<TmdbResultsDto> {
                override fun onResponse(call: Call<TmdbResultsDto>, response: Response<TmdbResultsDto>) {
                    val list = Converter.convertApiListToDtoList(response.body()?.docs)
                    repo.clearDB()
                    repo.putToDb(list)
                    callback.onSuccess(list)
                }

                override fun onFailure(call: Call<TmdbResultsDto>, t: Throwable) {
                    //В случае провала вызываем другой метод коллбека
                    callback.onFailure()
                }
            }
        )
    }

    fun getFilmsFromDB(): List<Film> = repo.getAllFromDB()

    fun getOneFilmFromDB(id: Int):Film = repo.getById(id)

    fun updateFilmInDb(film:Film) {
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
}