package com.wildtech.animals.di.component


import com.wildtech.animals.App
import com.wildtech.animals.di.ApplicationScope
import com.wildtech.animals.di.module.AndroidBindingModule
import com.wildtech.animals.di.module.AppModule
import com.wildtech.animals.di.module.DatabaseModule
import com.wildtech.animals.di.viewmodel.ViewModelModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@ApplicationScope
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        DatabaseModule::class,
        AndroidBindingModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<App> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()
}