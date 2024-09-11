package com.michael.securestore

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class SecureStoreModule {
    @Provides
    @Named("SecurePreferences_fileName")
    fun provideSecurePreferencesFileName(): String = "secret_shared_prefs"

    @Provides
    fun providesBiometricManager(context: Context) = androidx.biometric.BiometricManager.from(context)
}
