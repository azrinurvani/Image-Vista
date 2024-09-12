package com.azrinurvani.imagevista.presentation.search_screen

import androidx.lifecycle.ViewModel
import com.azrinurvani.imagevista.domain.repository.ImageRepository
import com.azrinurvani.imagevista.presentation.util.SnackBarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: ImageRepository
) : ViewModel() {

    private val _snackarEvent = Channel<SnackBarEvent>()
    val snackBarEvent = _snackarEvent.receiveAsFlow()

}