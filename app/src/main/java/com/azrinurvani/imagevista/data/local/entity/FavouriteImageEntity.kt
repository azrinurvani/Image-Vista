package com.azrinurvani.imagevista.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.azrinurvani.imagevista.data.util.Constants.FAVOURITE_IMAGE_TABLE

@Entity(tableName = FAVOURITE_IMAGE_TABLE)
data class FavouriteImageEntity(

    @PrimaryKey
    val id : String,
    val imageUrlSmall : String,
    val imageUrlRegular : String,
    val imageUrlRaw : String,
    val photographerName : String,
    val photographerUsername : String,
    val photographerProfileImageUrl : String,
    val photographerProfileLink : String,
    val width : Int,
    val height : Int,
    val description : String?
)