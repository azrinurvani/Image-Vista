package com.azrinurvani.imagevista.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UnsplashImageDto(
    @SerialName("id")
    val id: String = "",

    @SerialName("description")
    val description: String? = "",

    @SerialName("height")
    val height: Int = 0,

    @SerialName("width")
    val width: Int = 0,

    @SerialName("urls")
    val urls: UrlsDto? = UrlsDto(),

    @SerialName("user")
    val user: UserDto? = UserDto()
)

@Serializable
data class UrlsDto(
    @SerialName("full")
    val full: String = "",

    @SerialName("raw")
    val raw: String = "",

    @SerialName("regular")
    val regular: String = "",

    @SerialName("small")
    val small: String = "",

    @SerialName("thumb")
    val thumb: String = ""
)
