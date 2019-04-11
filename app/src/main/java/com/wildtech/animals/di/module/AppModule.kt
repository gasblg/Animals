package com.wildtech.animals.di.module

import android.content.Context
import com.wildtech.animals.App
import com.wildtech.animals.dao.KindDao
import com.wildtech.animals.di.ApplicationScope
import com.wildtech.animals.repository.MainRepository
import com.wildtech.animals.repository.MainRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides


@Module
abstract class AppModule {

    @Binds
    internal abstract fun bindContext(application: App): Context

    @Module
    companion object {
        @JvmStatic
        @Provides
        @ApplicationScope
        fun provideRepository(kindDao: KindDao): MainRepository = MainRepositoryImpl(kindDao)
    }

}
