package com.example.filmsSearch.domain

import com.example.filmsSearch.App
import com.example.filmsSearch.R
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
        when (getDefaultCategoryFromPreferences()){
            context.getString(R.string.radio_button_popular) ->
                retrofitService.getFilms(ApiKey.API,
                                         page,
                                         null,
                                         null,
                                         null)
                    .enqueue(object :
                    Callback<TmdbResultsDto> {
                        override fun onResponse(call: Call<TmdbResultsDto>, response: Response<TmdbResultsDto>) {
                            //При успехе мы вызываем метод передаем onSuccess и в этот коллбэк список фильмов
                            callback.onSuccess(Converter.convertApiListToDtoList(response.body()?.docs))
                        }

                        override fun onFailure(call: Call<TmdbResultsDto>, t: Throwable) {
                            //В случае провала вызываем другой метод коллбека
                            callback.onFailure()
                        }
                })
            context.getString(R.string.radio_button_top_rated) ->
                retrofitService.getFilms(ApiKey.API,
                                         page,
                                         "!null",
                                         null,
                                         null)
                    .enqueue(object :
                    Callback<TmdbResultsDto> {
                    override fun onResponse(call: Call<TmdbResultsDto>, response: Response<TmdbResultsDto>) {
                        //При успехе мы вызываем метод передаем onSuccess и в этот коллбэк список фильмов
                        callback.onSuccess(Converter.convertApiListToDtoList(response.body()?.docs))
                    }

                    override fun onFailure(call: Call<TmdbResultsDto>, t: Throwable) {
                        //В случае провала вызываем другой метод коллбека
                        callback.onFailure()
                    }
                })
            context.getString(R.string.radio_button_playing) ->
                retrofitService.getFilms(ApiKey.API,
                                        page,
                                        null,
                                        "true",
                                        null)
                    .enqueue(object :
                        Callback<TmdbResultsDto> {
                        override fun onResponse(call: Call<TmdbResultsDto>, response: Response<TmdbResultsDto>) {
                            //При успехе мы вызываем метод передаем onSuccess и в этот коллбэк список фильмов
                            callback.onSuccess(Converter.convertApiListToDtoList(response.body()?.docs))
                        }

                        override fun onFailure(call: Call<TmdbResultsDto>, t: Throwable) {
                            //В случае провала вызываем другой метод коллбека
                            callback.onFailure()
                        }
                })
            context.getString(R.string.radio_button_genres) ->
                retrofitService.getFilms(ApiKey.API,
                                        page,
                                        null,
                                        null,
                                        "комедия")
                    .enqueue(object :
                        Callback<TmdbResultsDto> {
                        override fun onResponse(call: Call<TmdbResultsDto>, response: Response<TmdbResultsDto>) {
                            //При успехе мы вызываем метод передаем onSuccess и в этот коллбэк список фильмов
                            callback.onSuccess(Converter.convertApiListToDtoList(response.body()?.docs))
                        }

                        override fun onFailure(call: Call<TmdbResultsDto>, t: Throwable) {
                            //В случае провала вызываем другой метод коллбека
                            callback.onFailure()
                        }
                })
        }
    }

    fun getFilmsLiveData(): List<Film>{
        //TODO
        return TODO("Provide the return value")
    }
    //Save preferences
    fun saveDefaultCategoryToPreferences(category: String) {
        preferences.saveDefaultCategory(category)
    }
    //Get preferences
    fun getDefaultCategoryFromPreferences() = preferences.getDefaultCategory()
}