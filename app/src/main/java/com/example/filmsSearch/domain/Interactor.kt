package com.example.filmsSearch.domain

import com.example.filmsSearch.data.Entity.TmdbResultsDto
import com.example.filmsSearch.data.MainRepository
import com.example.filmsSearch.data.TmdbApi
import com.example.filmsSearch.utils.ApiKey
import com.example.filmsSearch.utils.Converter
import com.example.filmsSearch.view.viewmodel.SharedViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Interactor(private val repo: MainRepository, private val retrofitService: TmdbApi) {
    fun getFilmsFromApi(page: Int, callback: SharedViewModel.ApiCallback) {
        retrofitService.getFilms(ApiKey.API, "ru-RU", page).enqueue(object :
            Callback<TmdbResultsDto> {
            override fun onResponse(call: Call<TmdbResultsDto>, response: Response<TmdbResultsDto>) {
                //onSuccess
                callback.onSuccess(Converter.convertApiListToDtoList(response.body()?.docs))
            }

            override fun onFailure(call: Call<TmdbResultsDto>, t: Throwable) {
                //onFailure
                callback.onFailure()
            }
        })
    }
}