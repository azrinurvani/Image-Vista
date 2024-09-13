package com.azrinurvani.imagevista.presentation.home_sreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.azrinurvani.imagevista.domain.model.UnsplashImage
import com.azrinurvani.imagevista.domain.repository.ImageRepository
import com.azrinurvani.imagevista.presentation.util.SnackBarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ImageRepository
) : ViewModel() {

    private val _snackBarEvent = Channel<SnackBarEvent>()
    val snackBarEvent = _snackBarEvent.receiveAsFlow()

    val images : StateFlow<PagingData<UnsplashImage>> = repository.getEditorialFeedImages()
        .catch {  exception -> //catch the exception
            _snackBarEvent.send(
                SnackBarEvent(message = "Something went wrong. ${exception.message}")
            )
        }
        .cachedIn(viewModelScope) //using PagingData always using chacedIn(viewModelScope)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = PagingData.empty()
        )

    val favouriteImageIds : StateFlow<List<String>> = repository.getFavouriteImageIds()
        .catch {  exception -> //catch the exception
            _snackBarEvent.send(
                SnackBarEvent(message = "Something went wrong. ${exception.message}")
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = emptyList()
        )

    fun toggleFavouriteStatus(image : UnsplashImage){
        viewModelScope.launch {
            try {
                repository.toggleFavouriteStatus(image)
            }catch (e : Exception){
                _snackBarEvent.send(
                    SnackBarEvent(message = "Something went wrong. ${e.message}")
                )
            }
        }
    }




    //TODO BEFORE USING REMOTE MEDIATOR
//    var images : List<UnsplashImage> by mutableStateOf(emptyList())
//        private set
//
//    init {
//        getImages()
//    }
//
//    private fun getImages(){
//        viewModelScope.launch {
//            try {
//                val result = repository.getEditorialFeedImages()
//                images = result
//            }catch (e: UnknownHostException){
//                _snackarEvent.send(
//                    SnackBarEvent(message = "No Internet Connection. Please Check your network.")
//                )
//            }catch (e: Exception){
//                _snackarEvent.send(
//                    SnackBarEvent(message = "Something went wrong: ${e.message}")
//                )
//            }
//        }
//    }
}