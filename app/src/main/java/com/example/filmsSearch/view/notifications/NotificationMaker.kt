package com.example.filmsSearch.view.notifications

import android.Manifest
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.db_module.entity.Film
import com.example.filmsSearch.R
import com.example.filmsSearch.receivers.Reminder
import com.example.filmsSearch.view.MainActivity
import java.util.Calendar

object NotificationMaker {
    fun createNotification(context: Context, film: Film){
        val mIntent = Intent(context, MainActivity::class.java)
        mIntent.putExtra("fragment_name","DetailsFragment")
        mIntent.putExtra("film",film)
        val pendingIntent = PendingIntent.getActivity(context, 0,mIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val builder = NotificationCompat.Builder(context!!, NotificationConstants.CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_outline_watch_later_24)
            setContentTitle(context.getString(R.string.watch_later))
            setContentText(film.title)
            priority = NotificationCompat.PRIORITY_DEFAULT
            setContentIntent(pendingIntent)
            setAutoCancel(true)
        }
        val notificationManager = NotificationManagerCompat.from(context)
        Glide.with(context)
            //say that bitmap needed
            .asBitmap()
            //show from what url download image
            .load(film.poster)
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {
                }
                //If positive received
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    //Make notification in Big Picture style
                    builder.setStyle(NotificationCompat.BigPictureStyle().bigPicture(resource))
                    //Renew notification
                    if (ActivityCompat.checkSelfPermission(
                            context,
                            Manifest.permission.POST_NOTIFICATIONS
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return
                    }
                    notificationManager.notify(film.id, builder.build())
                }
            })
//Send initial notification in standard view
        notificationManager.notify(film.id, builder.build())
    }
    fun notificationSet(context: Context, film: Film){
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDate = calendar.get(Calendar.DATE)
        val currentHour = calendar.get(Calendar.HOUR)
        val currentMinute = calendar.get(Calendar.MINUTE)

        DatePickerDialog(
            context,
            {
              _, dpdYear, dpdMonth, dpdDate ->
                val timeSetListener =
                    TimePickerDialog.OnTimeSetListener { _, hourOfDay, pickerMinute
                        ->
                        val pickedDateTime = Calendar.getInstance()
                        pickedDateTime.set(
                            dpdYear,
                            dpdMonth,
                            dpdDate,
                            hourOfDay,
                            pickerMinute,
                            0
                        )
                        val dateTimeInMillis = pickedDateTime.timeInMillis
                        createWatchLaterEvent(context, dateTimeInMillis, film)
                    }
                TimePickerDialog(
                    context,
                    timeSetListener,
                    currentHour,
                    currentMinute,
                    true
                ).show()
            },
            currentYear,
            currentMonth,
            currentDate
        ).show()
    }

    private fun createWatchLaterEvent(context: Context, dateTimeInMillis: Long, film: Film) {
        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        //Create intent
        val intent = Intent(film.title, null, context, Reminder()::class.java)
        //Set film
        val bundle = Bundle()
        bundle.putParcelable(NotificationConstants.FILM_KEY, film)
        intent.putExtra(NotificationConstants.FILM_BUNDLE_KEY, bundle)
        //Create pending intent for launching from outside the application
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        //Setting Alarm
        alarmManager.setExact(
            /* type = */ AlarmManager.RTC_WAKEUP,
            /* triggerAtMillis = */ dateTimeInMillis,
            /* operation = */ pendingIntent
        )
    }

}