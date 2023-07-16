package com.example.filmsSearch.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.filmsSearch.data.DAO.FilmDao
import com.example.filmsSearch.data.Entity.Film

@Database(entities = [Film::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun filmDao(): FilmDao
}