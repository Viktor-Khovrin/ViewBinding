package com.example.filmsSearch.data.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.filmsSearch.data.Entity.Film

@Dao
interface FilmDao {
    //Query for all from table
    @Query("SELECT * FROM cached_films")
    fun getCachedFilms(): List<Film>

    @Update
    fun updateFilmInDb(film: Film)

    //Put list into database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Film>)

    @Query("SELECT * FROM cached_films WHERE id =:id")
    fun getOneCashedFilm(id: Int): Film
}