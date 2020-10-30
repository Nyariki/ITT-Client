package com.itt.client.data.remote

/**
 * A generic class that holds a value with its loading state.
 * @param <T>
</T> */
data class Resource<out T>(
    val status: Status,
    val data: T?,
    val message: String?,
    val code: Int? = 200
) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun <T> error(msg: String, data: T?, code: Int?): Resource<T> {
            return Resource(
                Status.ERROR,
                data,
                msg,
                code
            )
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(
                Status.LOADING,
                data,
                null
            )
        }
    }
}