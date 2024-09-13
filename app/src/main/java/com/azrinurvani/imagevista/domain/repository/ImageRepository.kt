package com.azrinurvani.imagevista.domain.repository

import androidx.paging.PagingData
import com.azrinurvani.imagevista.domain.model.UnsplashImage
import kotlinx.coroutines.flow.Flow

interface ImageRepository {

//    suspend fun getEditorialFeedImages() : List<UnsplashImage>
    fun getEditorialFeedImages() : Flow<PagingData<UnsplashImage>>
    //only get image in one time using suspend and Object List or Data Object
    suspend fun getImage(imageId: String) : UnsplashImage

    //Using Flow because get data continues (get data again and again)
    fun searchImages(query: String) : Flow<PagingData<UnsplashImage>>
    fun getAllFavouriteImages() : Flow<PagingData<UnsplashImage>>

    suspend fun toggleFavouriteStatus(image : UnsplashImage) //call only in one time

    fun getFavouriteImageIds() : Flow<List<String>>

}