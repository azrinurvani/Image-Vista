package com.azrinurvani.imagevista.presentation.full_image_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.azrinurvani.imagevista.domain.model.UnsplashImage
import com.azrinurvani.imagevista.domain.repository.Downloader
import com.azrinurvani.imagevista.domain.repository.ImageRepository
import com.azrinurvani.imagevista.presentation.navigation.Routes
import com.azrinurvani.imagevista.presentation.util.SnackBarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class FullImageViewModel @Inject constructor(
    private val repository: ImageRepository,
    private val downloader : Downloader,
    savedStateHandle: SavedStateHandle //to keep arguments
) : ViewModel() {

    private val imageId = savedStateHandle.toRoute<Routes.FullImageScreen>().imageId

    private val _snackarEvent = Channel<SnackBarEvent>()
    val snackBarEvent = _snackarEvent.receiveAsFlow()

    var image : UnsplashImage? by mutableStateOf(null)
        private set


    init {
        getImage()
    }

    private fun getImage(){
        viewModelScope.launch {
            try {
                val result = repository.getImage(imageId)
                image = result
            }catch (e: UnknownHostException){
                _snackarEvent.send(
                    SnackBarEvent(message = "No Internet Connection. Please Check your network.")
                )
            }catch (e: Exception){
                _snackarEvent.send(
                    SnackBarEvent(message = "Something went wrong: ${e.message}")
                )
            }
        }
    }

    fun downloadImage(url : String, title : String?){
        viewModelScope.launch {
            try {
                downloader.downloadFile(url,title)
            }catch (e : Exception){
                _snackarEvent.send(
                    SnackBarEvent(message = "Something went wrong: ${e.message}")
                )
            }
        }
    }
}