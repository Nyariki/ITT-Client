package com.itt.client.view.report

import androidx.lifecycle.ViewModel
import com.itt.client.data.repository.RestRepository

class ReportActivityViewModel(private val restRepository: RestRepository) : ViewModel() {

    suspend fun fetchReports() = restRepository.fetchReports()
}