package com.flickr.ui.base

interface IBaseView {
    fun showMessage(message: String)

    fun isNetworkConnected(): Boolean

    fun showProgressBar()

    fun dismissProgressBar()
}