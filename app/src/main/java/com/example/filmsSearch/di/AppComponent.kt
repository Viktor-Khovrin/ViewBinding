package com.example.filmsSearch.di

import com.example.filmsSearch.di.modules.DatabaseModule
import com.example.filmsSearch.di.modules.DomainModule
import com.example.filmsSearch.di.modules.RemoteModule
import com.example.filmsSearch.view.viewmodel.DetailsFragmentViewModel
import com.example.filmsSearch.view.viewmodel.FavoritesFragmentViewModel
import com.example.filmsSearch.view.viewmodel.HomeFragmentViewModel
import com.example.filmsSearch.view.viewmodel.SettingsFragmentViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    //Внедряем все модули, нужные для этого компонента
    modules = [
        RemoteModule::class,
        DatabaseModule::class,
        DomainModule::class
    ]
)
interface AppComponent {
    //метод для того, чтобы появилась возможность внедрять зависимости в HomeFragmentViewModel
    fun inject(homeFragmentViewModel: HomeFragmentViewModel)

    fun inject(settingsFragmentViewModel: SettingsFragmentViewModel)

    fun inject(favoritesFragmentViewModel: FavoritesFragmentViewModel)

    fun inject(detailsFragmentViewModel: DetailsFragmentViewModel)
}