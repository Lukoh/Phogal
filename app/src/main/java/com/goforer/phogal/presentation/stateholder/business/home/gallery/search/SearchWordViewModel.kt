package com.goforer.phogal.presentation.stateholder.business.home.gallery.search

import com.goforer.base.extension.isNull
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
    fun setWord(word: String) {
        localDataSource.getSearchWords().isNull({
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

    fun removeWord(word: String) {
        localDataSource.getSearchWords().isNull({
        }, {
            val keywords = it.toMutableList()

            if (keywords.remove(word))
                localDataSource.setSearchWords(keywords.toList())
        })
    }
}