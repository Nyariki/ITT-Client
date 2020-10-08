package com.itt.client.data.repository

import com.itt.client.data.remote.RestRequests
import com.itt.client.makeNetworkCall

class RestRepository(var restRequests: RestRequests) {

    suspend fun startServer() = makeNetworkCall { restRequests.startServerAsync() }

    suspend fun fetchReports() = makeNetworkCall { restRequests.fetchReportsAsync() }
}


