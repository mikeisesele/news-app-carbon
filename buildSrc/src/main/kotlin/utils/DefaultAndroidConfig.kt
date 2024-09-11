package utils

import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.kotlin.dsl.PluginDependenciesSpecScope

@Suppress("UnstableApiUsage")
fun LibraryExtension.defaultAndroidConfig() {
    compileSdk = Configuration.defaultConfig.compileSdk

    defaultConfig {
        minSdk = Configuration.defaultConfig.minSdk
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
