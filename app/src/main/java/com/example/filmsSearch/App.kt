package com.example.filmsSearch

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.example.db_module.DaggerDbComponent
import com.example.db_module.DatabaseModule
import com.example.filmsSearch.di.AppComponent
import com.example.filmsSearch.di.DaggerAppComponent
import com.example.filmsSearch.di.modules.DomainModule
import com.example.filmsSearch.view.notifications.NotificationConstants.CHANNEL_ID
import com.example.remote_module.DaggerRemoteComponent
import com.example.remote_module.RemoteModule

class App : Application() {
    lateinit var dagger: AppComponent
    var isPromoShowed: Boolean = false

    override fun onCreate() {
        super.onCreate()
        instance = this
        //Create Component
        val remoteProvider = DaggerRemoteComponent.builder().remoteModule(RemoteModule()).build()
        val dbProvider = DaggerDbComponent.builder().databaseModule(DatabaseModule(instance)).build()
        dagger = DaggerAppComponent
            .builder()
            .remoteProvider(remoteProvider)
            .dbProvider(dbProvider)
            .domainModule(DomainModule(instance))
            .build()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //Set name, description and importance of channel
            val name = "WatchLaterChannel"
            val descriptionText = "FilmsSearch notification Channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            //Create channel, send in to parameters ID(string), name(string), importance(const)
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            //Separately set description
            mChannel.description = descriptionText
            //Make access to NotificationManager
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            //Registering the channel
            notificationManager.createNotificationChannel(mChannel)
        }
    }

    companion object {
        lateinit var instance: App
            private set
    }
}