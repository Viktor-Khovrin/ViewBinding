package com.example.filmsSearch

import android.app.Application
import com.example.filmsSearch.data.ApiConstants
import com.example.filmsSearch.data.TmdbApi
import com.example.filmsSearch.domain.Interactor
import com.example.filmsSearch.utils.UnsafeOkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {
    lateinit var interactor: Interactor

    override fun onCreate() {
        super.onCreate()
        //Инициализируем экземпляр App, через который будем получать доступ к остальным переменным
        instance = this
        val okHttpClient = UnsafeOkHttpClient.unsafeOkHttpClient
            //OkHttpClient.Builder()
//            .connectionSpecs(listOf(
//                ConnectionSpec.CLEARTEXT,ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
//                .allEnabledTlsVersions()
//                .allEnabledCipherSuites()
//                .build()))
//            .callTimeout(30, TimeUnit.SECONDS)
//            .readTimeout(30, TimeUnit.SECONDS)
//            .addInterceptor(HttpLoggingInterceptor().apply {
//                if (BuildConfig.DEBUG) {
//                    level = HttpLoggingInterceptor.Level.BASIC
//                }
//            })
//            .build()
        //Создаем Ретрофит
        val retrofit = Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        //Создаем сам сервис с методами для запросов
        val retrofitService = retrofit.create(TmdbApi::class.java)
        //Инициализируем интерактор
        interactor = Interactor(retrofitService)
    }

    companion object {
        //Здесь статически хранится ссылка на экземпляр App
        lateinit var instance: App
            //Приватный сеттер, чтобы нельзя было в эту переменную присвоить что-либо другое
            private set
    }
}