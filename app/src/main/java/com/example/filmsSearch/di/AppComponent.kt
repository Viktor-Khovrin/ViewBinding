package com.example.filmsSearch.di

import com.example.filmsSearch.di.modules.DatabaseModule
import com.example.filmsSearch.di.modules.DomainModule
import com.example.filmsSearch.di.modules.RemoteModule
import com.example.filmsSearch.view.viewmodel.SharedViewModel
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
    fun inject(sharedViewModel: SharedViewModel)
}