package com.pcr.drainit.utill

sealed class Resource<T>(
    val data: T? = null,
    val statusCode: Int? = null,
    val message: String? = null
) {
    class Success<T>(data: T, statusCode: Int? = null, message: String? = null) :
        Resource<T>(data, statusCode, message)

    class Error<T>(message: String, statusCode: Int? = null, data: T? = null) :
        Resource<T>(data, statusCode, message)
}
