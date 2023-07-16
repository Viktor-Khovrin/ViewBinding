package com.example.filmsSearch.di.modules

import android.content.Context
import androidx.room.Room
import com.example.filmsSearch.data.DAO.FilmDao
import com.example.filmsSearch.data.MainRepository
import com.example.filmsSearch.data.db.AppDatabase
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
class DatabaseModule {
    @Singleton
    @Provides
    fun provideFilmDao(context: Context) =
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