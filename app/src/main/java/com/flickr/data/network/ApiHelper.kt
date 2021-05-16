package com.flickr.data.network

import com.flickr.data.model.FlickrResponse
import retrofit2.Response
import retrofit2.http.*

/**
 * Retrofit service class for get response from url
 */
@JvmSuppressWildcards
interface ApiHelper {
    /**
     * Get photos from url
     */
    @GET(ApiUtils.REST)
    suspend fun searchPhotos(@QueryMap map: Map<String, Any>): Response<FlickrResponse>

}