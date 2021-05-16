package com.flickr.data.model

class CustomException() : Exception() {
    var code: Int = 0
    var error: String = ""
    var apiError: ApiError? = null

    constructor(code: Int, exception: ApiError?) : this() {
        this.code = code
        this.apiError = exception
    }

    constructor(code: Int, error: String) : this() {
        this.code = code
        this.error = error
    }
}

