import org.gradle.accessors.dm.LibrariesForLibs
import utils.composeConfiguration
import utils.defaultAndroidConfig
import utils.registerIncrementalDetektTask

val libs = the<LibrariesForLibs>()

plugins {
    id("com.android.application")
    id("com.diffplug.spotless")
    id("io.gitlab.arturbosch.detekt")
    kotlin("android")
    kotlin("kapt")
}


@Suppress("UnstableApiUsage")
android {
    defaultAndroidConfig()
    composeConfiguration(libs.versions.compose.compiler)
}

dependencies {
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

