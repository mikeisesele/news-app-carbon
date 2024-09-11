import org.gradle.accessors.dm.LibrariesForLibs
import utils.composeConfiguration
import utils.defaultAndroidConfig
import utils.registerIncrementalDetektTask
import utils.spotlessConfig

val libs = the<LibrariesForLibs>()

plugins {
    id("com.android.application")
    id("com.diffplug.spotless")
    id("io.gitlab.arturbosch.detekt")
    kotlin("android")
    kotlin("kapt")
}

spotlessConfig(libs.versions.spotless.ktlint)

@Suppress("UnstableApiUsage")
android {
    defaultAndroidConfig()
    composeConfiguration(libs.versions.compose.compiler)

    defaultConfig {
        applicationId = Configuration.defaultConfig.packageName
        versionCode = Configuration.defaultConfig.versionCode
        versionName = Configuration.defaultConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.firebase.crashlytics)
    coreLibraryDesugaring(libs.desugar)

    detektPlugins(libs.detekt.formatter)
}

registerIncrementalDetektTask(libs.versions.plugin.detekt.get())

android {
    namespace = "com.michael.baseapp"
}

kapt {
    correctErrorTypes = true
}

