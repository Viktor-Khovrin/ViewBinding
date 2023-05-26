package com.example.filmsSearch.di.modules

import com.example.filmsSearch.data.MainRepository
import com.example.filmsSearch.data.TmdbApi
import com.example.filmsSearch.domain.Interactor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {
    @Singleton
    @Provides
    fun provideInteractor(repository: MainRepository, tmdbApi: TmdbApi) = Interactor(repo = repository, retrofitService = tmdbApi)
}