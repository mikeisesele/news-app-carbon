package com.michael.securestore

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences.PrefKeyEncryptionScheme
import androidx.security.crypto.EncryptedSharedPreferences.PrefValueEncryptionScheme
import androidx.security.crypto.MasterKeys
import androidx.security.crypto.MasterKeys.AES256_GCM_SPEC
import com.google.firebase.crashlytics.FirebaseCrashlytics
import java.io.File
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

private const val DEFAULT_FILE_NAME = "secret_shared_prefs"

/**
 * EncryptedSharedPreferences with default filename
 *
 * The class exists to enforce the use of EncryptedSharedPreferences in the project since it is
 * encapsulated under SharedPreferences interface and cannot be referenced concretely.
 *
 */
@Suppress("TooManyFunctions")
// which are defined in android.content.SharedPreferences
@Singleton
class SecurePreferences @Inject constructor(
    context: Context,
    @Named("SecurePreferences_fileName")
    fileName: String = DEFAULT_FILE_NAME,
) : SharedPreferences {
    private val sharedPreferences: SharedPreferences

    init {
        val masterKeyAlias = MasterKeys.getOrCreate(AES256_GCM_SPEC)
        sharedPreferences = try {
            initSecurePrefs(fileName, masterKeyAlias, context)
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().recordException(e)
            context.clearSharedPrefs()
            initSecurePrefs(fileName, masterKeyAlias, context)
        }
    }

    private fun Context.clearSharedPrefs() {
        val dir = File("${this.filesDir.parent}/shared_prefs/")
        val children: Array<String> = dir.list() as Array<String>
        for (i in children.indices) {
            this.getSharedPreferences(children[i].replace(".xml", ""), Context.MODE_PRIVATE).edit()
                .clear().commit()
            File(dir, children[i]).delete()
        }
    }

    private fun initSecurePrefs(
        fileName: String,
        masterKeyAlias: String,
        context: Context,
    ) = EncryptedSharedPreferences.create(
        fileName,
        masterKeyAlias,
        context,
        PrefKeyEncryptionScheme.AES256_SIV,
        PrefValueEncryptionScheme.AES256_GCM,
    )

    override fun getAll(): MutableMap<String, *> = sharedPreferences.all

    override fun getString(p0: String?, p1: String?): String? = sharedPreferences.getString(p0, p1)

    override fun getStringSet(p0: String?, p1: MutableSet<String>?): MutableSet<String>? =
        sharedPreferences.getStringSet(p0, p1)

    override fun getInt(p0: String?, p1: Int): Int = sharedPreferences.getInt(p0, p1)

    override fun getLong(p0: String?, p1: Long): Long = sharedPreferences.getLong(p0, p1)

    override fun getFloat(p0: String?, p1: Float): Float = sharedPreferences.getFloat(p0, p1)

    override fun getBoolean(p0: String?, p1: Boolean): Boolean =
        sharedPreferences.getBoolean(p0, p1)

    override fun contains(p0: String?): Boolean = sharedPreferences.contains(p0)

    override fun edit(): SharedPreferences.Editor = sharedPreferences.edit()

    override fun registerOnSharedPreferenceChangeListener(p0: SharedPreferences.OnSharedPreferenceChangeListener?) {
        sharedPreferences.registerOnSharedPreferenceChangeListener(p0)
    }

    override fun unregisterOnSharedPreferenceChangeListener(p0: SharedPreferences.OnSharedPreferenceChangeListener?) {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(p0)
    }
}
