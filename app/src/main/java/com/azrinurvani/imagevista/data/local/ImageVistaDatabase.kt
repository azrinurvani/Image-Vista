package com.azrinurvani.imagevista.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.azrinurvani.imagevista.data.local.entity.FavouriteImageEntity

@Database(
    entities = [
        FavouriteImageEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ImageVistaDatabase : RoomDatabase() {

    abstract fun favouriteImagesDao() : FavouriteImagesDao
}