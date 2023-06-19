package com.goforer.phogal.presentation.stateholder.business.home.gallery.photos.search

import com.goforer.base.extension.isNull
import com.goforer.phogal.data.datasource.local.LocalDataSource
import com.goforer.phogal.data.model.remote.response.gallery.photo.photoinfo.Picture
import com.goforer.phogal.presentation.stateholder.business.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchWordViewModel
@Inject
constructor() : BaseViewModel<Picture>() {
    @Inject
    lateinit var localStorage: LocalDataSource

    fun setWord(keyword: String) {
        localStorage.getSearchWords().isNull({
            localStorage.setSearchWords(listOf(keyword.trim()))
        }, {
            val keywords = it.toMutableList()

            if (keywords.binarySearch(keyword.trim()) < 0) {
                if (it.size >= 7) {
                    keywords.removeFirst()
                    keywords.add(keyword)
                } else {
                    keywords.add(keyword)
                }

                localStorage.setSearchWords(keywords.toList())
            }
        })
    }

    fun getWords(): MutableList<String>? {
        val keywordList = localStorage.getSearchWords()?.asReversed()

        return if (keywordList.isNullOrEmpty()) {
            null
        } else {
            keywordList.toMutableList()
        }
    }
}