package com.flickr.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.flickr.R
import com.flickr.data.model.CustomException
import com.flickr.data.model.FlickrResponse
import com.flickr.data.network.ApiHelper
import com.flickr.data.network.BaseDataSource
import com.flickr.data.network.ResourceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class PhotoRepository @Inject constructor(
    private val application: Application,
    private val apiHelper: ApiHelper
) : BaseDataSource() {
    val apiResponse: MutableLiveData<ResourceState<FlickrResponse>> by lazy {
        MutableLiveData<ResourceState<FlickrResponse>>()
    }

    /**
     * Get Photos from api
     */
    suspend fun searchPhotos(map: HashMap<String, Any>) {
        withContext(Dispatchers.IO) {
            try {
                val response = apiHelper.searchPhotos(map)
                apiResponse.postValue(getResult(response))
            } catch (error: Exception) {
                if (error is ConnectException || error is SocketTimeoutException || error is SocketException || error is UnknownHostException) {
                    apiResponse.postValue(
                        ResourceState.error(
                            CustomException(
                                -1,
                                application.applicationContext.getString(R.string.api_no_internet)
                            )
                        )
                    )
                } else {
                    apiResponse.postValue(
                        ResourceState.error(
                            CustomException(
                                -1,
                                error.message
                                    ?: application.applicationContext.getString(R.string.api_something_wrong)
                            )
                        )
                    )
                }
            }
        }
    }
}

