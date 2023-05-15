package com.goforer.phogal.data.repository.paging.source.home

import androidx.paging.PagingState
import com.goforer.phogal.data.model.remote.response.photos.Document
import com.goforer.phogal.data.model.remote.response.photos.ImagesResponse
import com.goforer.phogal.data.network.api.Params
import com.goforer.phogal.data.network.api.RestAPI
import com.goforer.phogal.data.network.response.ApiResponse
import com.goforer.phogal.data.repository.paging.PagingErrorMessage
import com.goforer.phogal.data.repository.paging.PagingErrorMessage.PAGING_EMPTY
import com.goforer.phogal.data.repository.paging.PagingErrorMessage.PAGING_NORMAL
import com.goforer.phogal.data.repository.paging.source.BasePagingSource
import kotlinx.coroutines.flow.collectLatest
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetImagesPagingSource
@Inject
constructor() : BasePagingSource<Int, ImagesResponse, Document>() {
    private var pagingItemResponse: ImagesResponse? = null
    private var nextKey: Int? = null

    @Suppress("KotlinConstantConditions")
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Document> {
        val offset = params.key ?: 0

        nextKey = offset.plus(1)

        return try {
            request()
            when {
                errorMessage == PAGING_EMPTY -> LoadResult.Error(Throwable(errorMessage))
                errorMessage.contains("AccessDeniedError") -> LoadResult.Error(Throwable(errorMessage))
                pagingItemResponse?.documents?.size!! >= 0 -> {
                    if (pagingItemResponse?.meta?.is_end!!) {
                        nextKey = null
                    }

                    pagingItemResponse?.documents?.let {
                        pagingItemResponse?.documents = it.sortedByDescending { document ->
                            document.datetime
                        }.toMutableList()
                    }

                    LoadResult.Page(
                        data = pagingItemResponse?.documents!!,
                        prevKey = null,
                        nextKey = nextKey
                    )
                }
                pagingItemResponse?.meta?.is_end!! -> {
                    if (pagingItemResponse?.meta?.total_count!! > 0) {
                        if (pagingItemResponse?.documents?.isEmpty()!!)
                            errorMessage = PagingErrorMessage.PAGING_END
                    } else {
                        errorMessage = PAGING_EMPTY
                    }

                    LoadResult.Error(Throwable(errorMessage))
                }
                else -> LoadResult.Error(Throwable(errorMessage))
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

    override fun getRefreshKey(state: PagingState<Int, Document>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(pageSize) ?: anchorPage?.nextKey?.minus(pageSize)
        }
    }

    override suspend fun requestAPI(params: Params) {
        val page = nextKey ?: 1

        //In case of using Flow.combine...
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

        restAPI.getPhotoImages(
            RestAPI.AUTH_HEADER,
            params.args[0] as String,
            "recency",
            page,
            params.args[2] as Int
        ).collectLatest {
            pagingItemResponse = handleImagesResult(it)
        }
    }

    private fun handleImagesResult(response: ApiResponse<ImagesResponse>) : ImagesResponse? {
        val pagingItemResponse = handleResponse(response)

        pagingItemResponse?.let {
            errorMessage = if (errorMessage == PAGING_EMPTY
                && (it.meta.total_count == 0 || it.documents.isEmpty())) {
                PAGING_EMPTY
            } else {
                PAGING_NORMAL
            }
        }

        return pagingItemResponse
    }
}