package com.itt.client.view.main

import androidx.lifecycle.ViewModel
import com.itt.client.data.repository.RestRepository

class MainActivityViewModel(
    private val restRepository: RestRepository
) : ViewModel() {

    suspend fun startServer() = restRepository.startServer()

    suspend fun fetchCurrentEvents(
        time: String,
        colorStart: String?,
        colorStop: String?,
        colorReport: String?
    ) = restRepository.fetchCurrentEvents(time, colorStart, colorStop, colorReport)
}