package com.example.db_module

import android.content.Context
import androidx.room.Room
import com.example.db_module.dao.FilmDao
import com.example.db_module.db.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

//@Module
//class DatabaseModule {
//    @Singleton
//    @Provides
//    fun provideDatabaseHelper(context: Context) = DatabaseHelper(context)
//
//    @Provides
//    @Singleton
//    fun provideRepository(databaseHelper: DatabaseHelper) = MainRepository(databaseHelper)
//}
@Module
class DatabaseModule (private val context: Context) {
    @Singleton
    @Provides
    fun provideFilmDao() =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "film_db"
        )
            .fallbackToDestructiveMigration()
            .build().filmDao()

    @Singleton
    @Provides
    fun provideRepository(filmDao: FilmDao) = MainRepository(filmDao)
}