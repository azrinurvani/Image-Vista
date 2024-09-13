package com.azrinurvani.imagevista.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.azrinurvani.imagevista.data.local.entity.FavouriteImageEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface FavouriteImagesDao {

    @Query("SELECT * FROM favourite_images_table")
    fun getAllFavouriteImages() : PagingSource<Int,FavouriteImageEntity>

    @Upsert
    suspend fun insertFavouriteImage(image : FavouriteImageEntity)

    @Delete
    suspend fun deleteFavouriteImage(image : FavouriteImageEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM favourite_images_table WHERE id = :id)")
    //this EXISTS will check id is exist or not in favourite_images_table
    suspend fun isImageFavourite(id : String) : Boolean


    @Query("SELECT id FROM favourite_images_table")
    fun getFavouriteImagesId() : Flow<List<String>>
}