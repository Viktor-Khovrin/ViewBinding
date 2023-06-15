package com.example.filmsSearch.data.sp

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class PreferenceProvider(context: Context) {
    //Нам нужен контекст приложения
    private val appContext = context.applicationContext
    //Создаем экземпляр SharedPreferences
    private val preference: SharedPreferences = appContext.getSharedPreferences("settings", Context.MODE_PRIVATE)

    init {
        //Логика для первого запуска приложения, чтобы положить наши настройки,
        //Сюда потом можно добавить и другие настройки
        if(preference.getBoolean(KEY_FIRST_LAUNCH, false)) {
            preference.edit { putString(KEY_DEFAULT_CATEGORY, DEFAULT_CATEGORY) }
            preference.edit { putBoolean(KEY_FIRST_LAUNCH, false) }
        }
    }

    //Category prefs
    //Save category
    fun saveDefaultCategory(category: String) {
        preference.edit { putString(KEY_DEFAULT_CATEGORY, category) }
    }
    //Get category
    fun getDefaultCategory(): String {
        return preference.getString(KEY_DEFAULT_CATEGORY, DEFAULT_CATEGORY) ?: DEFAULT_CATEGORY
    }
    //Set query time
    fun setQueryTime(){
        preference.edit { putLong(KEY_API_QUERY_TIME, System.currentTimeMillis())}
    }
    //Set wrong query time for renew list
    fun setWrongQueryTime(){
        preference.edit { putLong(KEY_API_QUERY_TIME, System.currentTimeMillis()-10*60*1000) }
    }
    //Get query time
    fun getQueryTime(): Long {
        return preference.getLong(KEY_API_QUERY_TIME,0)
    }
    //Keys for prefs
    companion object {
        private const val KEY_FIRST_LAUNCH = "first_launch"
        private const val KEY_DEFAULT_CATEGORY = "default_category"
        private const val DEFAULT_CATEGORY = "popular"
        private const val KEY_API_QUERY_TIME = "query_time"
    }
}