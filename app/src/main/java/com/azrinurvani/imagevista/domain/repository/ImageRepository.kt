package com.azrinurvani.imagevista.domain.repository

import com.azrinurvani.imagevista.domain.model.UnsplashImage

interface ImageRepository {

    suspend fun getEditorialFeedImages() : List<UnsplashImage>
    suspend fun getImage(imageId: String) : UnsplashImage

}