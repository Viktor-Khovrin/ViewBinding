package com.example.filmsSearch.di

import com.example.filmsSearch.data.ApiConstants
import com.example.filmsSearch.data.MainRepository
import com.example.filmsSearch.data.TmdbApi
import com.example.filmsSearch.domain.Interactor
import com.example.filmsSearch.utils.UnsafeOkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DI {
    val mainModule = module {
        //Создаем репозиторий
        single { MainRepository() }
        //Создаем объект для получения данных из сети
        single<TmdbApi> {
            val okHttpClient = UnsafeOkHttpClient.unsafeOkHttpClient
//                OkHttpClient.Builder()
//                //Настраиваем таймауты для медленного интернета
//                .callTimeout(30, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS)
//                //Добавляем логгер
//                .addInterceptor(HttpLoggingInterceptor().apply {
//                    if (BuildConfig.DEBUG) {
//                        level = HttpLoggingInterceptor.Level.BASIC
//                    }
//                })
//                .build()
            //Создаем ретрофит
            val retrofit = Retrofit.Builder()
                //Указываем базовый URL из констант
                .baseUrl(ApiConstants.BASE_URL)
                //Добавляем конвертер
                .addConverterFactory(GsonConverterFactory.create())
                //Добавляем кастомный клиент
                .client(okHttpClient)
                .build()
            //Создаем сам сервис с методами для запросов
            retrofit.create(TmdbApi::class.java)
        }
        //Создаем интерактор
        single { Interactor(get(), get()) }
    }
}