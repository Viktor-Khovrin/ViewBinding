package com.example.filmsSearch.di.modules

import com.example.filmsSearch.data.ApiConstants
import com.example.filmsSearch.data.TmdbApi
import com.example.filmsSearch.utils.UnsafeOkHttpClient.unsafeOkHttpClient
import dagger.Module
import dagger.Provides
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RemoteModule {
//    @Provides
//    @Singleton
//    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
//        //Настраиваем таймауты для медленного интернета
//        .callTimeout(30, TimeUnit.SECONDS)
//        .readTimeout(30, TimeUnit.SECONDS)
//        //Добавляем логгер
//        .addInterceptor(HttpLoggingInterceptor().apply {
//            if (BuildConfig.DEBUG) {
//                level = HttpLoggingInterceptor.Level.BASIC
//            }
//        })
//        .build()

    @Provides
    @Singleton
    fun provideUnsafeOkHttpClient(): OkHttpClient = unsafeOkHttpClient


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        //Set base URL from constant
        .baseUrl(ApiConstants.BASE_URL)
        //Adding converter
        .addConverterFactory(GsonConverterFactory.create())
        //Adding RxJava
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        //Adding custom http client
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideTmdbApi(retrofit: Retrofit): TmdbApi = retrofit.create(TmdbApi::class.java)
}