package com.itt.client.data.repository

import com.itt.client.data.remote.RestRequests

class RestRepository(var restRequests: RestRequests) {

    suspend fun startServer() = restRequests.startServerAsync()
}


