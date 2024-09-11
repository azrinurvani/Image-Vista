package com.azrinurvani.imagevista.data.repository

import com.azrinurvani.imagevista.data.mapper.toDomainModel
import com.azrinurvani.imagevista.data.mapper.toDomainModelList
import com.azrinurvani.imagevista.data.remote.UnsplashApiService
import com.azrinurvani.imagevista.domain.model.UnsplashImage
import com.azrinurvani.imagevista.domain.repository.ImageRepository

class ImageRepositoryImpl(
    private val unsplashApi : UnsplashApiService
) : ImageRepository {


    override suspend fun getEditorialFeedImages(): List<UnsplashImage> {
        return unsplashApi.getEditorialFeedImages().toDomainModelList()
    }


    override suspend fun getImage(imageId: String): UnsplashImage {
        return unsplashApi.getImage(imageId).toDomainModel()
    }
}