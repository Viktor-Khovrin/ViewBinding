package com.example.db_module

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
//    dependencies = [AppDatabase::class],
    modules = [DatabaseModule::class])
interface DbComponent: DbProvider