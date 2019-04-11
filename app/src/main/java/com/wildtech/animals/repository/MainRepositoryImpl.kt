package com.wildtech.animals.repository

import androidx.paging.DataSource
import com.wildtech.animals.dao.KindDao
import com.wildtech.animals.model.Kind
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject


class MainRepositoryImpl @Inject constructor(private val kindDao: KindDao) : MainRepository {


    override fun add(kind: Kind): Completable =
        Completable.fromAction { kindDao.insert(kind) }

    override fun edit(kind: Kind): Completable =
        Completable.fromAction { kindDao.update(kind) }

    override fun delete(kind: Kind): Completable =
        Completable.fromAction { kindDao.delete(kind) }

    override fun getAll(): DataSource.Factory<Int, Kind> {
        return kindDao.getAll()
    }

    override fun getKind(id: Int): Single<Kind> {
        return kindDao.getById(id)
    }

}