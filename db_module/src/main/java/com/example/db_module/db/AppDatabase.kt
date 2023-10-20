package com.example.db_module.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.db_module.dao.FilmDao
import com.example.db_module.entity.Film

@Database(entities = [Film::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun filmDao(): FilmDao
}