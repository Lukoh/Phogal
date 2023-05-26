package com.goforer.phogal.data.network.api

import com.goforer.phogal.data.model.remote.response.gallery.photos.PhotosResponse
import com.goforer.phogal.data.model.remote.response.gallery.common.User
import com.goforer.phogal.data.model.remote.response.gallery.photo.Picture
import com.goforer.phogal.data.model.remote.response.gallery.common.Photo
import com.goforer.phogal.data.network.response.ApiResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RestAPI {
    @GET("search/photos")
    fun getPhotos(
        @Query("client_id") clientId: String,
        @Query("query") keyword: String,
        @Query("page") page: Int?,
        @Query("per_page") per_page: Int?,
    ): Flow<ApiResponse<PhotosResponse>>

    @GET("photos/{id}")
    fun getPhoto(
        @Path("id") id: String,
        @Query("client_id") clientId: String,
    ): Flow<ApiResponse<Picture>>

    @GET("users/{username}")
    fun getUserPublicProfile(
        @Path("username") username: String
    ): Flow<ApiResponse<User>>

    @GET("users/{username}/photos")
    fun getUserPhotos(
        @Path("username") username: String,
        @Query("client_id") clientId: String,
        @Query("page") page: Int?,
        @Query("per_page") per_page: Int?
    ): Flow<ApiResponse<MutableList<Photo>>>
}