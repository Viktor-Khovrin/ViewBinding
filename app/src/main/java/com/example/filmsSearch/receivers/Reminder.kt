package com.example.filmsSearch.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.db_module.entity.Film
import com.example.filmsSearch.view.notifications.NotificationConstants
import com.example.filmsSearch.view.notifications.NotificationMaker

class Reminder: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val bandle = intent?.getBundleExtra(NotificationConstants.FILM_BUNDLE_KEY)
        val film: Film = bandle?.get(NotificationConstants.FILM_KEY) as Film
        NotificationMaker.createNotification(context!!, film)
    }
}