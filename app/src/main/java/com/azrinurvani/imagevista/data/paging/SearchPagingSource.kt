package com.azrinurvani.imagevista.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.azrinurvani.imagevista.data.mapper.toDomainModelList
import com.azrinurvani.imagevista.data.remote.UnsplashApiService
import com.azrinurvani.imagevista.data.util.Constants.IV_LOG_TAG
import com.azrinurvani.imagevista.domain.model.UnsplashImage

class SearchPagingSource(
    private val query : String,
    private val unsplashApi : UnsplashApiService
) : PagingSource<Int, UnsplashImage>() {


    override fun getRefreshKey(state: PagingState<Int, UnsplashImage>): Int? {
        Log.d(IV_LOG_TAG, "getRefreshKey: ${state.anchorPosition}")
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashImage> {
        val currentPage = params.key ?: STARTING_PAGE_INDEX
        Log.d(IV_LOG_TAG, "currentPage: $currentPage")
        return try {

            val response = unsplashApi.searchImages(
                query = query,
                page = currentPage,
                perPage = params.loadSize
            )

            val endOfPaginationReached = response.images.isEmpty()
            Log.d(IV_LOG_TAG, "Load Result Response: ${response.images.toDomainModelList()}")
            Log.d(IV_LOG_TAG, "endOfPaginationReached: $endOfPaginationReached")
            LoadResult.Page(
                data = response.images.toDomainModelList(),
                prevKey = if (currentPage == STARTING_PAGE_INDEX) null else currentPage - 1,
                nextKey = if (endOfPaginationReached) null else currentPage + 1
            )

        }catch (e : Exception){
            Log.d(IV_LOG_TAG, "LoadResultError: ${e.message}")
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }
}