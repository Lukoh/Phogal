package com.goforer.phogal.presentation.stateholder.business.home.gallery.search

import com.goforer.base.extension.isNullOnFlow
import com.goforer.phogal.data.datasource.local.LocalDataSource
import com.goforer.phogal.data.model.remote.response.gallery.common.PhotoUiState
import com.goforer.phogal.presentation.stateholder.business.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchWordViewModel
@Inject
constructor(
    private val localDataSource: LocalDataSource
) : BaseViewModel<PhotoUiState>() {
    suspend fun setWord(word: String) {
        localDataSource.getSearchWords().isNullOnFlow({
            localDataSource.setSearchWords(listOf(word.trim()))
        }, {
            val keywords = it.toMutableList()

            if (keywords.binarySearch(word.trim()) < 0) {
                if (it.size >= 7) {
                    keywords.removeFirst()
                    keywords.add(word)
                } else {
                    keywords.add(word)
                }

                localDataSource.setSearchWords(keywords.toList())
            }
        })
    }

    fun getWords(): MutableList<String>? {
        val keywordList = localDataSource.getSearchWords()?.asReversed()

        return if (keywordList.isNullOrEmpty()) {
            null
        } else {
            keywordList.toMutableList()
        }
    }

    suspend fun removeWord(word: String) {
        localDataSource.getSearchWords().isNullOnFlow({
        }, {
            val keywords = it.toMutableList()

            if (keywords.remove(word))
                localDataSource.setSearchWords(keywords.toList())
        })
    }
}