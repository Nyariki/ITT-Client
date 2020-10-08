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
    @SerializedName("success")
    val success: Int? = 0

    @SerializedName("message")
    val message: String? = null

    val status: Boolean
        get() = success == 1
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