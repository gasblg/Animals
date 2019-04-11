package com.wildtech.animals.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Kind (

    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var name:String
)