package com.azrinurvani.imagevista.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UnsplashImagesSearchResult(
    @SerialName("results")
    val images: List<UnsplashImageDto> = listOf(),
    @SerialName("total")
    val total: Int = 0,
    @SerialName("total_pages")
    val totalPages: Int = 0
)