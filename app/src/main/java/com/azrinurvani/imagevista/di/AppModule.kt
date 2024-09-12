package com.azrinurvani.imagevista.di

import android.content.Context
import com.azrinurvani.imagevista.data.remote.UnsplashApiService
import com.azrinurvani.imagevista.data.repository.AndroidImageDownloader
import com.azrinurvani.imagevista.data.repository.ImageRepositoryImpl
import com.azrinurvani.imagevista.data.repository.NetworkConnectivityObserverImpl
import com.azrinurvani.imagevista.data.util.Constants.BASE_URL
import com.azrinurvani.imagevista.domain.repository.Downloader
import com.azrinurvani.imagevista.domain.repository.ImageRepository
import com.azrinurvani.imagevista.domain.repository.NetworkConnectivityObserver
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUnsplashService() : UnsplashApiService{
        val contentType = "application/json".toMediaType()
        val json = Json { ignoreUnknownKeys = true } //it's mean to ignore deleted fields from response
        val retrofit = Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory(contentType)) //Using ScalarsConverterFactory only getting JSON into a string object
            .baseUrl(BASE_URL)
            .build()
        return retrofit.create(UnsplashApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideImageRepository(
        apiService: UnsplashApiService
    ) : ImageRepository{
        return ImageRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideAndroidImageDownloader(@ApplicationContext context: Context) : Downloader{
        return AndroidImageDownloader(context)
    }

    @Provides
    @Singleton
    fun provideApplicationScope() : CoroutineScope{
        return CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }

    @Provides
    @Singleton
    fun provideNetworkConnectivityObserver(
        @ApplicationContext context: Context,
        scope : CoroutineScope
    ) : NetworkConnectivityObserver{
        return NetworkConnectivityObserverImpl(context,scope)
    }
}