package com.example.filmsSearch.data

import android.content.ContentValues
import android.database.Cursor
import com.example.filmsSearch.data.db.DatabaseHelper
import com.example.filmsSearch.domain.Film

class MainRepository(databaseHelper: DatabaseHelper) {
    private val sqlDB = databaseHelper.readableDatabase
    private lateinit var cursor: Cursor
    fun putToDb(film: Film) {
        val cv = ContentValues()
        cv.apply {
            put(DatabaseHelper.COLUMN_TITLE, film.title)
            put(DatabaseHelper.COLUMN_POSTER, film.poster)
            put(DatabaseHelper.COLUMN_DESCRIPTION, film.description)
            put(DatabaseHelper.COLUMN_RATING, film.rating)
            put(DatabaseHelper.COLUMN_FAVORITES,0)
        }
        sqlDB.insert(DatabaseHelper.TABLE_NAME, null, cv)
    }
    fun getAllFromDB(): List<Film> {
        cursor = sqlDB.rawQuery("SELECT * FROM ${DatabaseHelper.TABLE_NAME}", null)
        val result = mutableListOf<Film>()
        if (cursor.moveToFirst()) {
            do {
                val title = cursor.getString(1)
                val poster = cursor.getString(2)
                val description = cursor.getString(3)
                val rating = cursor.getDouble(4)
                val isInFavorites = cursor.getInt(5) != 0

                result.add(Film(title, poster, description, rating, isInFavorites))
            } while (cursor.moveToNext())
        }
        //Возвращаем список фильмов
        return result
    }
}