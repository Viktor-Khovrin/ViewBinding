package com.example.db_module.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.db_module.entity.Film
import io.reactivex.rxjava3.core.Observable

@Dao
interface FilmDao {
    //Query for all from table
    @Query("SELECT * FROM cached_films")
//    fun getCachedFilms(): LiveData<List<Film>>
    fun getCachedFilms(): Observable<List<Film>>

    @Update
    fun updateFilmInDb(film: Film)

    //Put list into database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Film>)

    //Clear DB
    @Query("DELETE FROM cached_films")
    fun clearCachedFilms()

    @Query("SELECT * FROM cached_films WHERE id =:id")
    fun getOneCashedFilm(id: Int): Film
}