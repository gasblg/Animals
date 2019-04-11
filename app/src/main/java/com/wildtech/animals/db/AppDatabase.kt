package com.wildtech.animals.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wildtech.animals.dao.KindDao
import com.wildtech.animals.model.Kind


@Database(entities =  [ Kind::class],  version = 1 , exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun kindDao(): KindDao

        }






