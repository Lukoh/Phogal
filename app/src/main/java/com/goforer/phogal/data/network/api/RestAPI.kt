package com.goforer.phogal.data.network.api

import com.goforer.phogal.BuildConfig.apiKey
import com.goforer.phogal.data.model.response.ImagesResponse
import com.goforer.phogal.data.network.response.ApiResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RestAPI {
    @GET("/v2/search/image")
    fun getPhotoImages(
        @Header("Authorization") apiKey: String = AUTH_HEADER,
        @Query("query") userId: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Flow<ApiResponse<ImagesResponse>>

    @GET("/v2/search/vclip")
    fun getVideoImages(
        @Header("Authorization") apiKey: String = AUTH_HEADER,
        @Query("query") userId: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Flow<ApiResponse<ImagesResponse>>

    companion object {
        const val AUTH_HEADER= "KakaoAK $apiKey"
    }
}