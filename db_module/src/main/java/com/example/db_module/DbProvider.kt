package com.example.db_module

import com.example.db_module.dao.FilmDao


interface DbProvider {
    fun provideDatabase(): FilmDao
}