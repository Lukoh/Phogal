package com.goforer.phogal.presentation.stateholder.business.home.gallery.photos.search

import com.goforer.base.extension.isNull
import com.goforer.base.storage.LocalStorage
import com.goforer.phogal.data.model.remote.response.gallery.photo.photoinfo.Picture
import com.goforer.phogal.presentation.stateholder.business.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class KeywordViewModel
@Inject
constructor() : BaseViewModel<Picture>() {
    @Inject
    lateinit var localStorage: LocalStorage

    fun setKeyword(keyword: String) {
        localStorage.getSearchKeywordList().isNull({
            localStorage.setSearchKeywordList(listOf(keyword))
        }, {
            val keywords = it.toMutableList()

            if (keywords.binarySearch(keyword.trim()) < 0) {
                if (it.size >= 5) {
                    keywords.removeFirst()
                    keywords.add(keyword)
                } else {
                    keywords.add(keyword)
                }

                localStorage.setSearchKeywordList(keywords.toList())
            }
        })
    }

    fun getKeywords(): MutableList<String>? {
        val keywordList = localStorage.getSearchKeywordList()

        return if (keywordList.isNullOrEmpty()) {
            null
        } else {
            keywordList.toMutableList()
        }
    }
}