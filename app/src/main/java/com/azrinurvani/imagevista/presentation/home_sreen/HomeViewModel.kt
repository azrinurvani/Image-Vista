package com.azrinurvani.imagevista.presentation.home_sreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azrinurvani.imagevista.domain.model.UnsplashImage
import com.azrinurvani.imagevista.domain.repository.ImageRepository
import com.azrinurvani.imagevista.presentation.util.SnackBarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ImageRepository
) : ViewModel() {
    var images : List<UnsplashImage> by mutableStateOf(emptyList())
        private set

    private val _snackarEvent = Channel<SnackBarEvent>()
    val snackBarEvent = _snackarEvent.receiveAsFlow()

    init {
        getImages()
    }

    private fun getImages(){
        viewModelScope.launch {
            try {
                val result = repository.getEditorialFeedImages()
                images = result
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
}