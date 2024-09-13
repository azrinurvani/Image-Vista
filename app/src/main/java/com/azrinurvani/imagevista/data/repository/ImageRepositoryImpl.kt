package com.azrinurvani.imagevista.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.azrinurvani.imagevista.data.local.ImageVistaDatabase
import com.azrinurvani.imagevista.data.mapper.toDomainModel
import com.azrinurvani.imagevista.data.mapper.toFavouriteImageEntity
import com.azrinurvani.imagevista.data.paging.EditorialFeedRemoteMediator
import com.azrinurvani.imagevista.data.paging.SearchPagingSource
import com.azrinurvani.imagevista.data.remote.UnsplashApiService
import com.azrinurvani.imagevista.data.util.Constants.ITEMS_PER_PAGE
import com.azrinurvani.imagevista.domain.model.UnsplashImage
import com.azrinurvani.imagevista.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ImageRepositoryImpl(
    private val unsplashApi : UnsplashApiService,
    private val database: ImageVistaDatabase
) : ImageRepository {

    private val favouriteImagesDao = database.favouriteImagesDao()
    private val editorialFeedDao = database.editorialFeedDao()

    //    override suspend fun getEditorialFeedImages(): List<UnsplashImage> {
//        return unsplashApi.getEditorialFeedImages().toDomainModelList()
//    }
    @OptIn(ExperimentalPagingApi::class)
    override fun getEditorialFeedImages(): Flow<PagingData<UnsplashImage>> {
        return Pager(
            config = PagingConfig(
                initialLoadSize = 20, //change initialLoadSize to 20 because as defaults initial load size is 3 * pageSize (in this case 3*10 = 30 items)
                pageSize = ITEMS_PER_PAGE
            ),
            remoteMediator = EditorialFeedRemoteMediator(unsplashApi,database),
            pagingSourceFactory = {
                editorialFeedDao.getAllEditorialFeedImages()
            }
        )
            .flow
            .map { pagingData ->
                pagingData.map { it.toDomainModel() }
            }
    }


    override suspend fun getImage(imageId: String): UnsplashImage {
        return unsplashApi.getImage(imageId).toDomainModel()
    }

    override fun searchImages(query: String): Flow<PagingData<UnsplashImage>> {
        return Pager(
            config = PagingConfig(
                initialLoadSize = 20, //change initialLoadSize to 20 because as defaults initial load size is 3 * pageSize (in this case 3*10 = 30 items)
                pageSize = ITEMS_PER_PAGE
            ),
            pagingSourceFactory = {
                SearchPagingSource(query,unsplashApi)
            }
        ).flow
    }

    override fun getAllFavouriteImages(): Flow<PagingData<UnsplashImage>> {
        return Pager(
            config = PagingConfig(
                initialLoadSize = 20, //change initialLoadSize to 20 because as defaults initial load size is 3 * pageSize (in this case 3*10 = 30 items)
                pageSize = ITEMS_PER_PAGE
            ),
            pagingSourceFactory = {
                favouriteImagesDao.getAllFavouriteImages()
            }
        )
            .flow
            .map { pagingData ->
                pagingData.map {
                    it.toDomainModel()
                }
            }
    }

    override suspend fun toggleFavouriteStatus(image: UnsplashImage) {
        val isFavourite = favouriteImagesDao.isImageFavourite(image.id)
        val favouriteImage = image.toFavouriteImageEntity()
        if (isFavourite){
            favouriteImagesDao.deleteFavouriteImage(image = favouriteImage)
        }else{
            favouriteImagesDao.insertFavouriteImage(image = favouriteImage)
        }
    }

    override fun getFavouriteImageIds(): Flow<List<String>> {
        return favouriteImagesDao.getFavouriteImagesId()
    }
}