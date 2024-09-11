@file:Suppress("ForbiddenComment")
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project
import utils.composeConfiguration
import utils.defaultAndroidConfig
import utils.spotlessConfig

val libs = the<LibrariesForLibs>()

plugins {
    id("com.android.library")
    id("com.diffplug.spotless")
    id("io.gitlab.arturbosch.detekt")
    id("kotlin-parcelize")
    kotlin("android")
    kotlin("kapt")
}


spotlessConfig(libs.versions.spotless.ktlint)


android {
    defaultAndroidConfig()
    composeConfiguration(libs.versions.compose.compiler)

    buildTypes {
        getByName("debug") {
            buildConfigField(
                "String",
                "APP_VERSION",
                "\"${Configuration.defaultConfig.versionName}\""
            )
        }
        getByName("release") {
            buildConfigField(
                "String",
                "APP_VERSION",
                "\"${Configuration.defaultConfig.versionName}\""
            )
        }
    }
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

    detektPlugins(libs.detekt.formatter)
}
