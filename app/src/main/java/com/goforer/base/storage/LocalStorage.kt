package com.goforer.base.storage

import android.annotation.SuppressLint
import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.goforer.phogal.data.model.remote.response.gallery.common.User
import com.goforer.phogal.data.model.remote.response.gallery.photo.photoinfo.Picture
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import timber.log.Timber
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalStorage
@Inject
constructor(val context: Context, cookieJar: PersistentCookieJar? = null) {
    companion object {
        const val key_bookmark_photos = "key_bookmark_photos"
        const val key_search_word_list = "search_word_list"
        const val key_following_user = "key_following_user"
    }

    private val spec = KeyGenParameterSpec.Builder(
        MasterKey.DEFAULT_MASTER_KEY_ALIAS,
        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
    )
        .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
        .setKeySize(MasterKey.DEFAULT_AES_GCM_MASTER_KEY_SIZE)
        .build()

    private val masterKey = MasterKey.Builder(context)
        .setKeyGenParameterSpec(spec)
        .build()

    private val pref = EncryptedSharedPreferences.create(
        context,
        "Encrypted_Shared_Preferences",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    internal fun logOut() {
        Timber.e("LocalStorage - Log out")

        clearPreference()
        deleteCache(context)
    }

    private fun deleteCache(context: Context) {
        runCatching {
            deleteDir(context.cacheDir)
        }.onFailure { e ->
            e.printStackTrace()
        }
    }

    private fun deleteDir(dir: File?): Boolean {
        return if (dir != null && dir.isDirectory) {
            val children = dir.list() ?: return false

            for (i in children.indices) {
                val success = deleteDir(File(dir, children[i]))
                if (!success) {
                    return false
                }
            }

            dir.delete()
        } else if (dir != null && dir.isFile) {
            dir.delete()
        } else {
            false
        }
    }

    @SuppressLint("ApplySharedPref")
    internal fun clearPreference() {
        Timber.e("LocalStorage - Clear session cookie")

        Timber.d("LocalStorage - Clear preference")
        val editor = pref.edit()

        editor.clear()
        editor.apply()
        editor.commit()
    }

    internal fun geBookmarkedPhotos(): MutableList<Picture>? {
        val json = pref.getString(key_bookmark_photos, null)
        val type = object : TypeToken<ArrayList<Picture>>() {}.type

        return Gson().fromJson(json, type)
    }

    internal fun isPhotoBookmarked(photo: Picture): Boolean {
        val photos = geBookmarkedPhotos()

        return if (photos.isNullOrEmpty()) {
            false
        } else {
            val foundPhoto = photos.find { it.id == photo.id || it.urls.raw == photo.urls.raw }

            foundPhoto != null
        }
    }

    internal fun setBookmarkPhoto(bookmarkedPhoto: Picture): MutableList<Picture>? {
        val editor = pref.edit()
        var photos = geBookmarkedPhotos()
        val json: String
        val type = object : TypeToken<ArrayList<Picture>>() {}.type

        if (photos.isNullOrEmpty()) {
            photos = mutableListOf()
            photos.add(bookmarkedPhoto)
            json = Gson().toJson(photos)
            editor.apply()
            editor.putString(key_bookmark_photos, json)
            editor.apply()
        } else {
            val photo = photos.find { it.id == bookmarkedPhoto.id || it.urls.raw == bookmarkedPhoto.urls.raw }

            if (photo == null)
                photos.add(bookmarkedPhoto)
            else
                photos.remove(photo)

            json = Gson().toJson(photos)
            editor.putString(key_bookmark_photos, json)
            editor.apply()
        }

        return Gson().fromJson(json, type)
    }

    internal fun getSearchWords(): List<String>? {
        val json = pref.getString(key_search_word_list, null)
        val type = object : TypeToken<ArrayList<String>>() {}.type

        return Gson().fromJson(json, type)
    }

    internal fun setSearchWords(words: List<String>? = null) {
        val editor = pref.edit()
        val json = Gson().toJson(words)

        editor.putString(key_search_word_list, json)
        editor.apply()
    }

    internal fun getFollowingUsers(): MutableList<User>? {
        val json = pref.getString(key_following_user, null)
        val type = object : TypeToken<ArrayList<User>>() {}.type

        return Gson().fromJson(json, type)
    }

    internal fun isUserFollowed(user: User): Boolean {
        val users = getFollowingUsers()

        return if (users.isNullOrEmpty()) {
            false
        } else {
            val foundUser = users.find { it.id == user.id && it.username == user.username }

            foundUser != null
        }
    }

    internal fun setFollowingUser(user: User): MutableList<User>? {
        val editor = pref.edit()
        var users = getFollowingUsers()
        val json: String
        val type = object : TypeToken<ArrayList<User>>() {}.type

        if (users.isNullOrEmpty()) {
            users = mutableListOf()
            users.add(user)
            json = Gson().toJson(users)
            editor.apply()
            editor.putString(key_following_user, json)
            editor.apply()
        } else {
            val followingUser = users.find { it.id == user.id && it.username == user.username }

            if (followingUser == null)
                users.add(user)
            else
                users.remove(followingUser)

            json = Gson().toJson(users)
            editor.putString(key_following_user, json)
            editor.apply()
        }

        return Gson().fromJson(json, type)
    }
}