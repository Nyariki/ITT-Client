package com.itt.client.data.remote

import com.itt.client.data.data.CurrentEventResponse
import com.itt.client.data.data.Event
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Created by Robert Mayore.
 */

interface RestRequests {

    @GET("start")
    suspend fun startServerAsync(): Response<ObjectResponse<String>>

    @GET("report")
    suspend fun fetchReportsAsync(): Response<ListResponse<Event>>

    @FormUrlEncoded
    @POST("get-current-events")
    suspend fun fetchCurrentEventsAsync(
        @Field("time") time: String,
        @Field("start_color") startColor: String?,
        @Field("stop_color") stopColor: String?,
        @Field("report_color") reportColor: String?
    ): Response<ObjectResponse<CurrentEventResponse>>
}
