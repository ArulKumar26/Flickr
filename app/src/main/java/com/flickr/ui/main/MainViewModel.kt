package com.flickr.ui.main

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.flickr.R
import com.flickr.data.model.CustomException
import com.flickr.data.network.ApiUtils
import com.flickr.data.network.ResourceState
import com.flickr.repository.PhotoRepository
import com.flickr.ui.base.BaseViewModel
import com.flickr.utils.Constants
import com.flickr.utils.NetworkUtils
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val application: Application,
    private val photoRepository: PhotoRepository
) : BaseViewModel() {
    val photoResponse by lazy { photoRepository.apiResponse }
    var searchText = ObservableField("")
    var page = ObservableField(1)

    fun searchPhotos() {
        viewModelScope.launch {
            try {
                if (NetworkUtils.isNetWorkAvailable(application.applicationContext)) {
                    photoResponse.value = ResourceState.loading()

                    val map = HashMap<String, Any>()

                    map[ApiUtils.METHOD] = Constants.METHOD
                    map[ApiUtils.API_KEY] = Constants.API_KEY
                    map[ApiUtils.FORMAT] = Constants.FORMAT
                    map[ApiUtils.JSON_CALLBACK] = Constants.NO_JSON_CALLBACK
                    map[ApiUtils.SAFE_SEARCH] = Constants.SAFE_SEARCH
                    map[ApiUtils.TEXT] = searchText.get()!!
                    map[ApiUtils.PAGE] = page.get()!!

                    photoRepository.searchPhotos(map)
                }
            } catch (error: Exception) {
                photoResponse.postValue(
                    ResourceState.error(
                        CustomException(
                            -2,
                            error.message
                                ?: application.applicationContext.getString(R.string.api_something_wrong)
                        )
                    )
                )
            }
        }
    }
}