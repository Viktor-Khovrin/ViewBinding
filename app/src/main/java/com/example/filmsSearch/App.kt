package com.example.filmsSearch

import android.app.Application
import com.example.filmsSearch.di.AppComponent
import com.example.filmsSearch.di.DaggerAppComponent
import com.example.filmsSearch.di.modules.DatabaseModule
import com.example.filmsSearch.di.modules.DomainModule
import com.example.remote_module.DaggerRemoteComponent

class App : Application() {
    lateinit var dagger: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        //Create Component
        val remoteProvider = DaggerRemoteComponent.create()
        dagger = DaggerAppComponent
            .builder()
            .remoteProvider(remoteProvider)
            .databaseModule(DatabaseModule())
            .domainModule(DomainModule(this))
            .build()
    }

    companion object {
        lateinit var instance: App
            private set
    }
}