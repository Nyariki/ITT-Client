package com.itt.client.data.remote

import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by Robert Mayore on 10/8/2018.
 */

interface RestRequests {

    @GET("start")
    suspend fun startServerAsync(): Response<ObjectResponse<Any?>>
}
