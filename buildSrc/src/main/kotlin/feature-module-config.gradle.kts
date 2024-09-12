@file:Suppress("ForbiddenComment")
import org.gradle.accessors.dm.LibrariesForLibs
import utils.composeConfiguration
import utils.defaultAndroidConfig

val libs = the<LibrariesForLibs>()

plugins {
    id("com.android.library")
    id("kotlin-parcelize")
    kotlin("android")
    kotlin("kapt")
}

android {
    defaultAndroidConfig()
    composeConfiguration(libs.versions.compose.compiler)
}


dependencies {
    coreLibraryDesugaring(libs.desugar)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.androidx.core.testing)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.kotest.assertions)
    testImplementation(libs.turbine)
    testRuntimeOnly(libs.junit.engine)
}
