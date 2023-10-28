package com.example.filmsSearch.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.filmsSearch.R

class PowerListener: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent == null) return
        when (intent.action){
            Intent.ACTION_BATTERY_LOW -> Toast.makeText(context,context?.getString(R.string.battery_is_low),Toast.LENGTH_SHORT).show()
            Intent.ACTION_POWER_CONNECTED -> Toast.makeText(context, context?.getString(R.string.power_cord), Toast.LENGTH_SHORT).show()
        }
    }
}