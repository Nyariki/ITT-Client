package com.itt.client

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.location.LocationManager
import android.view.View
import android.widget.Toast
import androidx.core.location.LocationManagerCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.itt.client.data.remote.Resource
import com.itt.client.data.remote.RestResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.flow.flow
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.DecimalFormat
import java.util.concurrent.TimeUnit


fun Activity.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.showToast(message: String) {
    Toast.makeText(this.activity, message, Toast.LENGTH_SHORT).show()
}


fun Activity.showSnack(message: String) {
    val snackBar = Snackbar.make(
        this.findViewById(android.R.id.content),
        message, Snackbar.LENGTH_LONG
    )
    snackBar.show()
}

fun Activity.showSnack(view: View, message: String) {
    val snackBar = Snackbar.make(
        view,
        message, Snackbar.LENGTH_LONG
    )
    snackBar.show()
}

fun Fragment.showSnack(message: String) {
    this.activity?.apply {
        Snackbar.make(
            findViewById(android.R.id.content),
            message, Snackbar.LENGTH_LONG
        ).show()
    }
}

inline fun <E : Any, T : Collection<E>> T?.withNotNullNorEmpty(func: T.() -> Unit): T? {
    if (this != null && this.isNotEmpty()) {
        with(this) { func() }
    }
    return this
}

inline fun <E : Any, T : Collection<E>, R : Any> T?.whenNotNullNorEmpty(func: (T) -> R?): R? {
    if (this != null && this.isNotEmpty()) {
        return func(this)
    }
    return null
}

inline fun <E : Any, T : Collection<E>> T?.withNullOrEmpty(func: () -> Unit): T? {
    if (this == null || this.isEmpty()) {
        func()
    }
    return this
}

inline fun <E : Any, T : Collection<E>, R : Any> T?.whenNullOrEmpty(func: () -> R?): R? {
    if (this == null || this.isEmpty()) {
        return func()
    }
    return null
}

fun <E> Iterable<E>.replace(old: E, new: E) = map { if (it == old) new else it }

fun Context.isLocationEnabled(): Boolean {
    val locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return LocationManagerCompat.isLocationEnabled(locationManager)
}

fun createOkHttpClient(shouldAuthenticate: Boolean, token: String = ""): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
        .apply {
            addInterceptor { chain ->
                val request = chain.request().newBuilder()
                if (shouldAuthenticate) {
                    request.addHeader("Authorization", "Bearer $token")
                }
                request.addHeader("Accept", "application/json")
                request.addHeader("Cache-Control", "no-cache")
                chain.proceed(request.build())
            }

        }
        .build()
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory()).build()
    return retrofit.create(T::class.java)
}

fun Int.amountToHumanReadable(): String {
    return DecimalFormat("#,###").format(this.toLong())
}

suspend fun <RESPONSE : Any> makeNetworkCall(function: suspend () -> Response<RESPONSE>) = flow {
    emit(Resource.loading(data = null))
    try {
        val response = function()
        emit(
            if (response.code() in 200..299) Resource.success(response.body())
            else {
                val body : RestResponse<Any> = Gson().fromJson<RestResponse<Any>>(response.errorBody()!!.string())
                Resource.error(body.message?: "", null, response.code())
            }
        )
    } catch (e: Exception) {
        emit(
            if (e is HttpException)
                Resource.error(
                    "${e.message} | code ${e.response()?.code()}",
                    null,
                    e.response()?.code()
                )
            else
                Resource.error("${e.message}", null, null)
        )
        e.printStackTrace()
    }
}

fun RecyclerView.addItemDecorationWithoutLastDivider() {

    if (layoutManager !is LinearLayoutManager)
        return

    addItemDecoration(object :
        DividerItemDecoration(context, (layoutManager as LinearLayoutManager).orientation) {

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)

            if (parent.getChildAdapterPosition(view) == state.itemCount - 1)
                outRect.setEmpty()
            else
                super.getItemOffsets(outRect, view, parent, state)
        }
    })
}

inline fun <reified T> Gson.fromJson(json: String) = fromJson<T>(json, object: TypeToken<T>() {}.type)


