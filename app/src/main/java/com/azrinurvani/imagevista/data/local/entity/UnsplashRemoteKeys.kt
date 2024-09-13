package com.azrinurvani.imagevista.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.azrinurvani.imagevista.data.util.Constants.UNSPLASH_REMOTE_KEYS_TABLE

@Entity(tableName = UNSPLASH_REMOTE_KEYS_TABLE)
data class UnsplashRemoteKeys(

    @PrimaryKey
    val id : String,
    val prevPage : Int?,
    val nextPage : Int?

)
