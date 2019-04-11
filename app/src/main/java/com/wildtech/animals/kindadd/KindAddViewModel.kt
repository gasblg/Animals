package com.wildtech.animals.kindadd

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wildtech.animals.model.Kind
import com.wildtech.animals.parent.BaseViewModel
import com.wildtech.animals.repository.MainRepository
import com.wildtech.animals.utils.SingleLiveEvent
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class KindAddViewModel @Inject constructor(private val mainRepository: MainRepository) : BaseViewModel() {

    private val status = SingleLiveEvent<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status


    private fun add(kindText: String) {
        disposables += mainRepository.add(Kind(0, kindText))
            .subscribeOn(Schedulers.io())
            .subscribeBy(onComplete = {
                status.postValue(true)
            }, onError = {
                Log.e("error", "add kind")
            })
    }

    val nameKind = MutableLiveData<String>()

    private val nameKindData: LiveData<String>
        get() = nameKind


    fun addKind() {
        val name = nameKindData.value
        if (name != null) {
            add(name)
        } else {
            status.postValue(false)
        }
    }

}