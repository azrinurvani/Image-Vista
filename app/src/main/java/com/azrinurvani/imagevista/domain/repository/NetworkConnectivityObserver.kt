package com.azrinurvani.imagevista.domain.repository

import com.azrinurvani.imagevista.domain.model.NetworkStatus
import kotlinx.coroutines.flow.StateFlow

interface NetworkConnectivityObserver {
    val networkStatus : StateFlow<NetworkStatus>
}