package com.wildtech.animals.di.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wildtech.animals.kindadd.KindAddViewModel
import com.wildtech.animals.kindedit.KindEditViewModel
import com.wildtech.animals.kindlist.KindListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(KindListViewModel::class)
    fun bindListKindViewModel(viewModel: KindListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(KindAddViewModel::class)
    fun bindAddKindViewModel(viewModel: KindAddViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(KindEditViewModel::class)
    fun bindEditKindViewModel(viewModel: KindEditViewModel): ViewModel

}
