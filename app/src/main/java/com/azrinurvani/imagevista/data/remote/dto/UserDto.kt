package com.azrinurvani.imagevista.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("links")
    val links: UserLinksDto = UserLinksDto(),

    @SerialName("name")
    val name: String = "",

    @SerialName("profile_image")
    val profileImage: ProfileImageDto = ProfileImageDto(),

    @SerialName("username")
    val username: String = ""
)

@Serializable
data class ProfileImageDto(
    @SerialName("small")
    val small: String = ""
)

@Serializable
data class UserLinksDto(
    @SerialName("html")
    val html: String = "",

    @SerialName("likes")
    val likes: String = "",

    @SerialName("photos")
    val photos: String = "",

    @SerialName("portfolio")
    val portfolio: String = "",

    @SerialName("self")
    val self: String = ""
)