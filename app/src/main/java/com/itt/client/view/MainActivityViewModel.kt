package com.itt.client.view

import androidx.lifecycle.ViewModel
import com.itt.client.data.repository.RestRepository

class MainActivityViewModel(
    private val restRepository: RestRepository
) : ViewModel() {

    suspend fun startServer() = restRepository.startServer()
}