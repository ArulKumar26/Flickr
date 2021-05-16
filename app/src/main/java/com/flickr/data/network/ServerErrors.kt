package com.flickr.data.network

import android.content.Context
import com.flickr.R

class ServerErrors {
    companion object {
        private const val FAILED_CONNECT = -1
        private const val BAD_REQUEST = 400
        private const val UNAUTHORIZED_ACCESS = 401
        private const val NOT_FOUND = 404
        private const val INTERNAL_SERVER_ERROR = 500

        fun getErrorMsg(context: Context, code: Int): String {
            return when (code) {
                FAILED_CONNECT -> context.getString(R.string.api_failed_connect)
                BAD_REQUEST -> context.getString(R.string.api_error_check_input)
                UNAUTHORIZED_ACCESS -> context.getString(R.string.api_error_unauthorized_access)
                NOT_FOUND -> context.getString(R.string.api_error_not_found)
                INTERNAL_SERVER_ERROR -> context.getString(R.string.api_error_internal_error)
                else -> context.getString(R.string.api_something_wrong)
            }
        }
    }
}