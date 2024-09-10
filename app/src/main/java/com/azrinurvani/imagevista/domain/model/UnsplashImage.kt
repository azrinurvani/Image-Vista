package com.azrinurvani.imagevista.domain.model

data class UnsplashImage(
    val id : String,
    val imageUrlSmall : String,
    val imageUrlRegular : String,
    val imageUrlRaw : String,
    val photographerName : String,
    val photographerUsername : String,
    val photographerProfileImageUrl : String,
    val photographerProfileLink : String,
    val width : Int,
    val height : Int,
    val description : String?,
)
