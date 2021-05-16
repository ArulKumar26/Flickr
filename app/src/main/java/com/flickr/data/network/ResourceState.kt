package com.flickr.data.network

import com.flickr.data.model.CustomException

/**
 * A generic class that holds a value with its loading status.
 *
 */

data class ResourceState<out T>(val status: Status, val response: T?, val customException: CustomException?) {
    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): ResourceState<T> {
            return ResourceState(Status.SUCCESS, data, null)
        }

        fun <T> error(customException: CustomException, data: T? = null): ResourceState<T> {
            return ResourceState(Status.ERROR, data, customException)
        }

        fun <T> loading(): ResourceState<T> {
            return ResourceState(Status.LOADING, null, null)
        }
    }
}