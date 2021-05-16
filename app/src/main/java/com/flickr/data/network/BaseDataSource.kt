package com.flickr.data.network

import com.flickr.data.model.ApiError
import com.flickr.data.model.CustomException
import com.google.gson.Gson
import retrofit2.Response

/**
 * Abstract Base Data source class with error handling
 */
abstract class BaseDataSource {
    protected fun <T> getResult(response: Response<T>): ResourceState<T> {
        try {
            var apiError: ApiError? = null
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    return ResourceState.success(body)
                }
            } else {
                val errorBody = response.errorBody()
                val error = errorBody?.string()
                apiError = Gson().fromJson(error, ApiError::class.java)
            }
            return errorCheck(CustomException(response.code(), apiError))

        } catch (e: Exception) {
            return errorCheck(
                CustomException(
                    1000,
                    "Our server is under maintenance. We will resolve shortly!"
                )
            )
        }
    }

    private fun <T> errorCheck(customException: CustomException): ResourceState<T> {
        return ResourceState.error(customException)
    }
}

