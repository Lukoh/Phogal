package com.goforer.base.storage

import android.annotation.SuppressLint
import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import timber.log.Timber
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalStorage
@Inject
constructor(val context: Context, cookieJar: PersistentCookieJar? = null) {
    companion object {
        const val key_images = "images"
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
}