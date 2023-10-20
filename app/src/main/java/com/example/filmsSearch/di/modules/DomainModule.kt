package com.example.filmsSearch.di.modules

import android.content.Context
import com.example.db_module.MainRepository
import com.example.db_module.dao.FilmDao
import com.example.filmsSearch.data.sp.PreferenceProvider
import com.example.filmsSearch.domain.Interactor
import com.example.remote_module.TmdbApi
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
    fun provideRepository(filmDao: FilmDao) = MainRepository(filmDao)

    @Singleton
    @Provides
    fun provideInteractor(repository: MainRepository,
                          tmdbApi: TmdbApi,
                          preferenceProvider: PreferenceProvider)
            = Interactor(repo = repository,
                         retrofitService = tmdbApi,
                         preferences = preferenceProvider)
}