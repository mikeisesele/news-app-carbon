import org.gradle.accessors.dm.LibrariesForLibs
import utils.composeConfiguration
import utils.defaultAndroidConfig

val libs = the<LibrariesForLibs>()

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}


@Suppress("UnstableApiUsage")
android {
    defaultAndroidConfig()
    composeConfiguration(libs.versions.compose.compiler)
    testOptions {
        unitTests {
            all {
                it.enabled = true
            }
        }
    }
}

dependencies {
    coreLibraryDesugaring(libs.desugar)
}

android {
    namespace = "com.michael.baseapp"
}

kapt {
    correctErrorTypes = true
}

