package com.example.pizza_singh_capstone_project.interfaces

//sealed class BaseResponse<out T> {
//    data class Success<out T>(val data: T? = null) : BaseResponse<T>()
//    data class Loading(val nothing: Nothing?=null) : BaseResponse<Nothing>()
//    data class Error(val msg: String?) : BaseResponse<Nothing>()
//}

sealed class NetworkResult<T>(
    val data: T? = null,
    val message: String? = null
) {

    class Success<T>(data: T) : NetworkResult<T>(data)

    class Error<T>(message: String?, data: T? = null) : NetworkResult<T>(data, message)

    class Loading<T> : NetworkResult<T>()

}