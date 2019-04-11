package com.wildtech.animals.kindlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.wildtech.animals.model.Kind
import com.wildtech.animals.parent.BaseViewModel
import com.wildtech.animals.repository.MainRepository
import com.wildtech.animals.utils.SingleLiveEvent
import io.reactivex.Observable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class KindListViewModel @Inject constructor(private val mainRepository: MainRepository) : BaseViewModel() {

    private val status = SingleLiveEvent<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    val kindList: Observable<PagedList<Kind>> =
        RxPagedListBuilder(mainRepository.getAll(), PAGE_SIZE)
            .buildObservable()

    fun delete(kind: Kind) {
        disposables += mainRepository.delete(kind)
            .subscribeOn(Schedulers.io())
            .subscribeBy(onComplete = {
                status.postValue(true)
            }, onError = {
                status.postValue(false)
            })
    }

    companion object {
        private const val PAGE_SIZE = 20
    }

    private val click = SingleLiveEvent<Any>()
    val clickFab: LiveData<Any>
        get() = click

    fun createNewKind() {
        click.call()
    }

    private val list = MutableLiveData<ArrayList<Int>>()

    val observableList: LiveData<ArrayList<Int>>
        get() = list

    private val state = SingleLiveEvent<Boolean>()

    val observableState: LiveData<Boolean>
        get() = state

    fun saveState(listForDelete: ArrayList<Int>, stateActionMode: Boolean) {
        list.value = listForDelete
        state.value = stateActionMode
    }


}