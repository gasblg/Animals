package com.wildtech.animals.parent

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {
    protected val disposables: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        disposables.clear()
    }
}