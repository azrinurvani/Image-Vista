package com.azrinurvani.imagevista.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.azrinurvani.imagevista.data.mapper.toDomainModel
import com.azrinurvani.imagevista.data.mapper.toDomainModelList
import com.azrinurvani.imagevista.data.paging.SearchPagingSource
import com.azrinurvani.imagevista.data.remote.UnsplashApiService
import com.azrinurvani.imagevista.data.util.Constants.ITEMS_PER_PAGE
import com.azrinurvani.imagevista.domain.model.UnsplashImage
import com.azrinurvani.imagevista.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow

class ImageRepositoryImpl(
    private val unsplashApi : UnsplashApiService
) : ImageRepository {


    override suspend fun getEditorialFeedImages(): List<UnsplashImage> {
        return unsplashApi.getEditorialFeedImages().toDomainModelList()
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
}