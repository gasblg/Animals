package com.wildtech.animals.dao

import androidx.paging.DataSource
import androidx.room.*
import com.wildtech.animals.model.Kind
import io.reactivex.Single

@Dao
interface KindDao {

        @Query("SELECT * FROM kind WHERE id = :id")
        fun getById(id: Int): Single<Kind>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insert(kind: Kind)

        @Update
        fun update(kind: Kind)

        @Delete
        fun delete(kind: Kind)

        @Query("SELECT * FROM kind")
        fun getAll(): DataSource.Factory<Int, Kind>


}