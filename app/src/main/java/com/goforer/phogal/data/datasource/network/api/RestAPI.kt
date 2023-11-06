package com.goforer.phogal.data.datasource.network.api

import com.goforer.phogal.data.model.remote.response.gallery.photos.PhotosResponseUiState
import com.goforer.phogal.data.model.remote.response.gallery.common.UserUiState
import com.goforer.phogal.data.model.remote.response.gallery.photo.like.LikeResponseUiState
import com.goforer.phogal.data.datasource.network.response.ApiResponse
import com.goforer.phogal.data.model.remote.response.gallery.common.PhotoUiState
import kotlinx.coroutines.flow.Flow
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RestAPI {
    @GET("search/photos")
    fun getPhotos(
        @Query("client_id") clientId: String,
        @Query("query") keyword: String,
        @Query("page") page: Int?,
        @Query("per_page") per_page: Int?
    ): Flow<ApiResponse<PhotosResponseUiState>>

    @GET("photos/{id}")
    fun getPhoto(
        @Path("id") id: String,
        @Query("client_id") clientId: String
    ): Flow<ApiResponse<PhotoUiState>>

    @GET("users/{username}")
    fun getUserPublicProfile(
        @Path("username") username: String
    ): Flow<ApiResponse<UserUiState>>

    @GET("users/{username}/photos")
    fun getUserPhotos(
        @Path("username") username: String,
        @Query("client_id") clientId: String,
        @Query("page") page: Int?,
        @Query("per_page") per_page: Int?
    ): Flow<ApiResponse<MutableList<PhotoUiState>>>

    @POST("photos/{id}/like")
    fun postLike(
        @Path("id") id: String,
        @Query("client_id") clientId: String
    ): Flow<ApiResponse<LikeResponseUiState>>

    @DELETE("photos/{id}/like")
    fun deleteLike(
        @Path("id") id: String,
        @Query("client_id") clientId: String
    ): Flow<ApiResponse<LikeResponseUiState>>

    @GET("photos")
    fun getPopularPhotos(
        @Query("client_id") clientId: String,
        @Query("order_by") orderBy: String,
        @Query("page") page: Int?,
        @Query("per_page") per_page: Int?
    ): Flow<ApiResponse<MutableList<PhotoUiState>>>
}