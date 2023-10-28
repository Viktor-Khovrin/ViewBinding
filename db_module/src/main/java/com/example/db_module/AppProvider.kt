package com.example.db_module

import android.content.Context

interface AppProvider {
    fun provideContext(): Context
}