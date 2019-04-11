package com.wildtech.animals.di.module

import com.wildtech.animals.kindadd.KindAddFragment
import com.wildtech.animals.kindedit.KindEditFragment
import com.wildtech.animals.kindlist.KindListFragment
import com.wildtech.animals.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface AndroidBindingModule {

    @ContributesAndroidInjector
    fun mainActivity(): MainActivity

    @ContributesAndroidInjector
    fun kindListFragment(): KindListFragment

    @ContributesAndroidInjector
    fun kindAddFragment(): KindAddFragment

    @ContributesAndroidInjector
    fun kindEditFragment(): KindEditFragment
}