package com.azrinurvani.imagevista.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Routes {

    @Serializable
    data object HomeScreen : Routes()

    @Serializable
    data object SearchScreen : Routes()

    @Serializable
    data object FavouriteScreen : Routes()

    @Serializable
    data class FullImageScreen(val imageId : String) : Routes() //using data class because we should pass the imageId

    @Serializable
    data class ProfileScreen(val profileLink : String) : Routes()

}