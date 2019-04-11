package com.wildtech.animals

import com.wildtech.animals.di.component.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class App: DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerApplicationComponent.builder().create(this)

}