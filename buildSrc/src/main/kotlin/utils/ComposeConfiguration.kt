@file:Suppress("UnstableApiUsage")
package utils

import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.provider.Provider

fun LibraryExtension.composeConfiguration(composeCompilerVersion: Provider<String>) {
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = composeCompilerVersion.get()
    }
}

fun BaseAppModuleExtension.composeConfiguration(composeCompilerVersion: Provider<String>) {
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = composeCompilerVersion.get()
    }
}