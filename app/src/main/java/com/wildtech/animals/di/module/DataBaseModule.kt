package com.wildtech.animals.di.module

import android.content.Context
import androidx.room.Room
import com.wildtech.animals.db.AppDatabase
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @Provides
    fun provideDb(context: Context): AppDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "animalsFactory"
        )
            .build()

    @Provides
    fun provideKindDao(appDatabase: AppDatabase) = appDatabase.kindDao()


}


