package com.wildtech.animals.repository

import androidx.paging.DataSource
import com.wildtech.animals.model.Kind
import io.reactivex.Completable
import io.reactivex.Single

interface MainRepository {

    fun add(kind: Kind): Completable

    fun edit(kind: Kind): Completable

    fun delete(kind: Kind): Completable

    fun getAll(): DataSource.Factory<Int, Kind>

    fun getKind(id: Int): Single<Kind>

}