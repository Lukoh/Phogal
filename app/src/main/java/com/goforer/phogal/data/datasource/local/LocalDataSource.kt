package com.goforer.phogal.data.datasource.local

import android.annotation.SuppressLint
import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.goforer.phogal.data.model.remote.response.gallery.common.PhotoUiState
import com.goforer.phogal.data.model.remote.response.gallery.common.UserUiState
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import timber.log.Timber
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource
@Inject
constructor(val context: Context, cookieJar: PersistentCookieJar? = null) {
    companion object {
        const val key_bookmark_photos = "key_bookmark_photos"
        const val key_search_word_list = "search_word_list"
        const val key_following_user = "key_following_user"
        const val key_notification_following_enabled = "key_notification_following_enabled"
        const val key_notification_latest_enabled = "key_notification_latest_enabled"
        const val key_notification_community_enabled = "key_notification_community_enabled"
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
        Timber.e("LocalDataSource - Log out")

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
        Timber.e("LocalDataSource - Clear session cookie")

        Timber.d("LocalDataSource - Clear preference")
        val editor = pref.edit()

        editor.clear()
        editor.apply()
        editor.commit()
    }

    internal fun geBookmarkedPhotos(): MutableList<PhotoUiState>? {
        val json = pref.getString(key_bookmark_photos, null)
        val type = object : TypeToken<ArrayList<PhotoUiState>>() {}.type

        return Gson().fromJson(json, type)
    }

    internal fun isPhotoBookmarked(id: String, url: String): Boolean {
        val photos = geBookmarkedPhotos()

        return if (photos.isNullOrEmpty()) {
            false
        } else {
            val foundPhoto = photos.find { it.id == id || it.urls.raw == url }

            foundPhoto != null
        }
    }

    internal fun isPhotoBookmarked(id: String): Boolean {
        val photos = geBookmarkedPhotos()

        return if (photos.isNullOrEmpty()) {
            false
        } else {
            val foundPhoto = photos.find { it.id == id }

            foundPhoto != null
        }
    }

    internal fun setBookmarkPhoto(photoUiState: PhotoUiState): MutableList<PhotoUiState>? {
        val editor = pref.edit()
        var photos = geBookmarkedPhotos()
        val json: String
        val type = object : TypeToken<ArrayList<PhotoUiState>>() {}.type

        if (photos.isNullOrEmpty()) {
            photos = mutableListOf()
            photos.add(photoUiState)
            json = Gson().toJson(photos)
            editor.apply()
            editor.putString(key_bookmark_photos, json)
            editor.apply()
        } else {
            val photo = photos.find { it.id == photoUiState.id || it.urls.raw == photoUiState.urls.raw }

            if (photo == null)
                photos.add(photoUiState)
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

    internal fun getFollowingUsers(): MutableList<UserUiState>? {
        val json = pref.getString(key_following_user, null)
        val type = object : TypeToken<ArrayList<UserUiState>>() {}.type

        return Gson().fromJson(json, type)
    }

    internal fun isUserFollowed(user: UserUiState): Boolean {
        val users = getFollowingUsers()

        return if (users.isNullOrEmpty()) {
            false
        } else {
            val foundUser = users.find { it.id == user.id && it.username == user.username }

            foundUser != null
        }
    }

    internal fun setFollowingUser(user: UserUiState): MutableList<UserUiState>? {
        val editor = pref.edit()
        var users = getFollowingUsers()
        val json: String
        val type = object : TypeToken<ArrayList<UserUiState>>() {}.type

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

    var enabledFollowingNotification: Boolean
        get() = pref.getBoolean(key_notification_following_enabled, true)
        set(value) {
            pref.edit()
                .putBoolean(key_notification_following_enabled, value)
                .apply()
        }

    var enabledLatestNotification: Boolean
        get() = pref.getBoolean(key_notification_latest_enabled, true)
        set(value) {
            pref.edit()
                .putBoolean(key_notification_latest_enabled, value)
                .apply()
        }

    var enableCommunityNotification: Boolean
        get() = pref.getBoolean(key_notification_community_enabled, true)
        set(value) {
            pref.edit()
                .putBoolean(key_notification_community_enabled, value)
                .apply()
        }
}