package com.example.filmsSearch.di.modules

import android.content.Context
import com.example.filmsSearch.data.MainRepository
import com.example.remote_module.TmdbApi
import com.example.filmsSearch.data.sp.PreferenceProvider
import com.example.filmsSearch.domain.Interactor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule (val context: Context){
    @Provides
    fun provideContext () = context

    @Singleton
    @Provides
    fun providePreferences(context: Context) = PreferenceProvider(context)

    @Singleton
    @Provides
    fun provideInteractor(repository: MainRepository,
                          tmdbApi: com.example.remote_module.TmdbApi,
                          preferenceProvider: PreferenceProvider)
            = Interactor(repo = repository,
                         retrofitService = tmdbApi,
                         preferences = preferenceProvider)
}