package utils

import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion

@Suppress("UnstableApiUsage")
fun LibraryExtension.defaultAndroidConfig() {
    compileSdk = Configuration.defaultConfig.compileSdk

    defaultConfig {
        minSdk = Configuration.defaultConfig.minSdk
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
        isCoreLibraryDesugaringEnabled = true
    }

    buildFeatures {
        buildConfig = true
    }
}

@Suppress("UnstableApiUsage")
fun BaseAppModuleExtension.defaultAndroidConfig() {
    compileSdk = Configuration.defaultConfig.compileSdk

    defaultConfig {
        minSdk = Configuration.defaultConfig.minSdk
        targetSdk = Configuration.defaultConfig.targetSdk
        versionCode = Configuration.defaultConfig.versionCode
        versionName = Configuration.defaultConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isDebuggable = true
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
        isCoreLibraryDesugaringEnabled = true
    }
    buildFeatures {
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}
