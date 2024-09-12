import org.gradle.accessors.dm.LibrariesForLibs
import utils.defaultAndroidConfig

val libs = the<LibrariesForLibs>()

plugins {
    id("com.android.library")
    id("kotlin-kapt")
    kotlin("android")
    kotlin("kapt")
}

android {
    defaultAndroidConfig()
}

dependencies {
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.androidx.core.testing)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.kotest.assertions)
    testImplementation(libs.turbine)
    testRuntimeOnly(libs.junit.engine)
    coreLibraryDesugaring(libs.desugar)
}
