package com.flickr.ui.base

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    //If api value empty means we can use this value
    val noData by lazy { ObservableField(false) }
}