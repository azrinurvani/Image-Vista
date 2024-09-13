package com.azrinurvani.imagevista.data.util

import com.azrinurvani.imagevista.BuildConfig

object Constants {

    const val IV_LOG_TAG = "ImageVistaLogs"

    const val API_KEY = BuildConfig.UNSPLASH_API_KEY
    const val BASE_URL = "https://api.unsplash.com/"
    const val ITEMS_PER_PAGE = 10

    const val IMAGE_VISTA_DATABASE = "unsplash_image.db"
    const val FAVOURITE_IMAGE_TABLE = "favourite_images_table"
    const val UNSPLASH_IMAGE_TABLE = "images_table"
    const val UNSPLASH_REMOTE_KEYS_TABLE = "remote_keys_table"
}