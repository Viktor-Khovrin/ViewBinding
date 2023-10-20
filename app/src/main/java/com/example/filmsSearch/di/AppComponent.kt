package com.example.filmsSearch.di

import com.example.db_module.DbProvider
import com.example.filmsSearch.di.modules.DomainModule
import com.example.filmsSearch.view.viewmodel.DetailsFragmentViewModel
import com.example.filmsSearch.view.viewmodel.FavoritesFragmentViewModel
import com.example.filmsSearch.view.viewmodel.HomeFragmentViewModel
import com.example.filmsSearch.view.viewmodel.SettingsFragmentViewModel
import com.example.remote_module.RemoteProvider
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [RemoteProvider::class,
                    DbProvider::class],
//        DatabaseModule::class],
    modules = [
        DomainModule::class//,
        //DatabaseModule::class
    ]
)

interface AppComponent {
    fun inject(homeFragmentViewModel: HomeFragmentViewModel)

    fun inject(settingsFragmentViewModel: SettingsFragmentViewModel)

    fun inject(favoritesFragmentViewModel: FavoritesFragmentViewModel)

    fun inject(detailsFragmentViewModel: DetailsFragmentViewModel)
}