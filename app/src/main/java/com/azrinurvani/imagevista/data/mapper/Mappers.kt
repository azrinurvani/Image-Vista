package com.azrinurvani.imagevista.data.mapper

import com.azrinurvani.imagevista.data.local.entity.FavouriteImageEntity
import com.azrinurvani.imagevista.data.local.entity.UnsplashImageEntity
import com.azrinurvani.imagevista.data.remote.dto.UnsplashImageDto
import com.azrinurvani.imagevista.domain.model.UnsplashImage

fun UnsplashImageDto.toDomainModel() : UnsplashImage{
    return UnsplashImage(
        id = this.id,
        imageUrlSmall = this.urls?.small.toString(),
        imageUrlRegular = this.urls?.regular.toString(),
        imageUrlRaw = this.urls?.raw.toString(),
        photographerName = this.user?.name.toString(),
        photographerUsername = this.user?.username.toString(),
        photographerProfileImageUrl = this.user?.profileImage.toString(),
        photographerProfileLink = this.user?.links?.html.toString(),
        width = this.width,
        height = this.height,
        description = this.description
    )
}

fun UnsplashImageDto.toEntity() : UnsplashImageEntity{
    return UnsplashImageEntity(
        id = this.id,
        imageUrlSmall = this.urls?.small.toString(),
        imageUrlRegular = this.urls?.regular.toString(),
        imageUrlRaw = this.urls?.raw.toString(),
        photographerName = this.user?.name.toString(),
        photographerUsername = this.user?.username.toString(),
        photographerProfileImageUrl = this.user?.profileImage.toString(),
        photographerProfileLink = this.user?.links?.html.toString(),
        width = this.width,
        height = this.height,
        description = this.description
    )
}


fun UnsplashImage.toFavouriteImageEntity() : FavouriteImageEntity{
    return FavouriteImageEntity(
        id = this.id,
        imageUrlSmall = this.imageUrlSmall,
        imageUrlRegular = this.imageUrlRegular,
        imageUrlRaw = this.imageUrlRaw,
        photographerName = this.photographerName,
        photographerUsername = this.photographerUsername,
        photographerProfileImageUrl = this.photographerProfileImageUrl,
        photographerProfileLink = this.photographerProfileLink,
        width = this.width,
        height = this.height,
        description = this.description
    )
}

fun FavouriteImageEntity.toDomainModel() : UnsplashImage{
    return UnsplashImage(
        id = this.id,
        imageUrlSmall = this.imageUrlSmall,
        imageUrlRegular = this.imageUrlRegular,
        imageUrlRaw = this.imageUrlRaw,
        photographerName = this.photographerName,
        photographerUsername = this.photographerUsername,
        photographerProfileImageUrl = this.photographerProfileImageUrl,
        photographerProfileLink = this.photographerProfileLink,
        width = this.width,
        height = this.height,
        description = this.description
    )
}

fun UnsplashImageEntity.toDomainModel() : UnsplashImage{
    return UnsplashImage(
        id = this.id,
        imageUrlSmall = this.imageUrlSmall,
        imageUrlRegular = this.imageUrlRegular,
        imageUrlRaw = this.imageUrlRaw,
        photographerName = this.photographerName,
        photographerUsername = this.photographerUsername,
        photographerProfileImageUrl = this.photographerProfileImageUrl,
        photographerProfileLink = this.photographerProfileLink,
        width = this.width,
        height = this.height,
        description = this.description
    )
}



fun List<UnsplashImageDto>.toDomainModelList() : List<UnsplashImage>{
    return this.map { it.toDomainModel() }
}

fun List<UnsplashImageDto>.toEntityList() : List<UnsplashImageEntity>{
    return this.map { it.toEntity() }
}