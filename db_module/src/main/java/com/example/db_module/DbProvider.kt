package com.example.db_module


interface DbProvider {
    fun provideDatabase(): MainRepository
}