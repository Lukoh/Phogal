package com.goforer.phogal.data.repository.gallery.paging

import androidx.paging.PagingState
import com.goforer.phogal.BuildConfig
import com.goforer.phogal.data.model.local.error.ErrorThrowable
import com.goforer.phogal.data.model.remote.response.gallery.photos.PhotosResponseUiState
import com.goforer.phogal.data.datasource.network.api.Params
import com.goforer.phogal.data.datasource.network.response.Status.*
import com.goforer.phogal.data.model.remote.response.gallery.common.PhotoUiState
import com.goforer.phogal.data.repository.BasePagingSource
import com.google.gson.Gson
import kotlinx.coroutines.flow.collectLatest
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetPhotosPagingSource
@Inject
constructor() : BasePagingSource<Int, PhotosResponseUiState, PhotoUiState>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoUiState> {
        val page = params.key ?: 1

        return try {
            request(page)
            when(resource.status) {
                SUCCESS -> {
                    LoadResult.Page(
                        data = (resource.data as PhotosResponseUiState).results,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if ((resource.data as PhotosResponseUiState).results.isEmpty()) null else page + 1
                    )
                }
                ERROR -> {
                    val error = ErrorThrowable(
                        code = resource.errorCode,
                        message = resource.message.toString()
                    )
                    val gson = Gson()
                    val json = gson.toJson(error)
                    LoadResult.Error(Throwable(json.toString()))
                }
                LOADING -> TODO()
            }
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        } catch (exception: Exception) {
            // Handle errors in this block
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PhotoUiState>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun requestAPI(params: Params, nextKey: Int) {
        //In case of getting photos and videos at the same time, use Flow.combine...
        /*
        val imageFlow = restAPI.getPhotoImages(
            RestAPI.AUTH_HEADER,
            params.args[0] as String,
            "recency",
            page,
            params.args[2] as Int
        )
        val videoFlow = restAPI.getVideoImages(
            RestAPI.AUTH_HEADER,
            params.args[0] as String,
            "recency",
            page,
            params.args[2] as Int
        )

        imageFlow.combine(videoFlow) { response1, response2 ->
            val responseFirst = handleImagesResult(response1)
            val responseSecond = handleImagesResult(response2)

            responseFirst?.meta?.total_count?.let {
                responseFirst.meta.total_count = it.plus(responseSecond?.meta?.total_count!!)
                responseFirst.meta.pageable_count = it.plus(responseSecond.meta.pageable_count)
                responseFirst.documents.addAll(responseSecond.documents)
                val isEnd = if (responseFirst.meta.is_end) {
                    responseSecond.meta.is_end
                } else
                    false

                responseFirst.meta.is_end = isEnd
                responseFirst
            }

        }.collectLatest {
            pagingItemResponse = it
        }

         */

        restAPI.getPhotos(
            BuildConfig.clientId,
            params.args[0] as String,
            nextKey,
            params.args[1] as Int
        ).collectLatest {
            handleResponse(it)
        }
    }
}