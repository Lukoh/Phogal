package com.goforer.phogal.presentation.stateholder.business.home.common.bookmark

import com.goforer.base.storage.LocalStorage
import com.goforer.phogal.data.model.remote.response.gallery.photo.photoinfo.Picture
import com.goforer.phogal.presentation.stateholder.business.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel
@Inject
constructor() : BaseViewModel<Picture>() {
    @Inject
    lateinit var localStorage: LocalStorage

    fun setBookmarkPicture(picture: Picture) {
        localStorage.setBookmarkPhoto(picture)
    }

    fun getBookmarkPictures(): MutableList<Picture>? = localStorage.geBookmarkedPhotos()

    fun isPhotoBookmarked(picture: Picture) = localStorage.isPhotoBookmarked(picture)
}