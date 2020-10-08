package com.itt.client.data.remote

import com.google.gson.annotations.SerializedName
import java.util.*

class RestResponse<T : Any> : BaseApiResponse() {
    @SerializedName("data")
    val data: T? = null
}

class ObjectResponse<T> : BaseApiResponse() {
    val `object`: T? = null
}

class ListResponse<T> : BaseApiResponse() {
    val list: ArrayList<T>? = null
}


abstract class BaseApiResponse {
    @SerializedName("status")
    val status: Boolean? = false

    @SerializedName("message")
    val message: String? = null
}

class LoginResponse {
    @SerializedName("token")
    val token: String? = null
}

class RequestPasswordResetResponse {
    @SerializedName("email")
    val email: String? = null

    @SerializedName("otp")
    val otp: String? = null
}

class PasswordResetResponse : BaseApiResponse()