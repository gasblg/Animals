package com.wildtech.animals.kindedit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wildtech.animals.model.Kind
import com.wildtech.animals.parent.BaseViewModel
import com.wildtech.animals.repository.MainRepository
import com.wildtech.animals.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class KindEditViewModel @Inject constructor(private val mainRepository: MainRepository) : BaseViewModel() {

    private var existValue = false

    private val status = SingleLiveEvent<Boolean>()
    val observableStatus: LiveData<Boolean>
        get() = status


    fun edit() {
        val id = observableKind.value!!.id
        val name = nameKindData.value.toString()

        disposables += mainRepository.edit(Kind(id, name))
            .subscribeOn(Schedulers.io())
            .subscribeBy(onComplete = {
                status.postValue(true)
            }, onError = {
                status.postValue(false)
            })
    }

    private val kind = MutableLiveData<Kind>()
    private val observableKind: LiveData<Kind>
        get() = kind


    fun getKind(id: Int) {
        disposables += mainRepository.getKind(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    if (nameKindData.value.isNullOrEmpty() && !existValue) {
                        nameKind.value = it.name
                        kind.value = it
                        existValue = true
                    }
                },
                onError = {
                    Log.e( "error", "edit kind")
                })
    }


    val nameKind = MutableLiveData<String>()
    private val nameKindData: LiveData<String>
        get() = nameKind

    private val click = SingleLiveEvent<Boolean>()
    val clickToSave: LiveData<Boolean>
        get() = click

    fun editKind() {
        val name = nameKindData.value
        if (name != null && name.isNotEmpty()) {
            click.postValue(true)
        } else {
            click.postValue(false)

        }

    }


}


