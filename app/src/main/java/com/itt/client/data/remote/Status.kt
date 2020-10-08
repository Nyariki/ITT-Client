package com.learning.kifaru.data.remote

/* Status of a resource that is provided to the UI.
*
*
* These are usually created by the Repository classes where they return
* `LiveData<Resource<T>>` to pass back the latest data to the UI with its fetch state.
*/
enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}