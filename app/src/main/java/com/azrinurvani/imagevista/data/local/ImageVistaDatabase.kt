package com.azrinurvani.imagevista.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.azrinurvani.imagevista.data.local.entity.FavouriteImageEntity
import com.azrinurvani.imagevista.data.local.entity.UnsplashImageEntity
import com.azrinurvani.imagevista.data.local.entity.UnsplashRemoteKeys

@Database(
    entities = [
        FavouriteImageEntity::class,
        UnsplashImageEntity::class,
        UnsplashRemoteKeys::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ImageVistaDatabase : RoomDatabase() {

    abstract fun favouriteImagesDao() : FavouriteImagesDao
    abstract fun editorialFeedDao() : EditorialFeedDao
}