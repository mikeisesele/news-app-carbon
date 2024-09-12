import utils.spotlessConfig
import java.util.Properties

plugins {
    `core-module-config`
    alias(libs.plugins.hilt.plugin)
    alias(libs.plugins.kotlinSerialization)
}

val keystoreFile: File = project.rootProject.file("gradle.properties")
val properties = Properties()
properties.load(keystoreFile.inputStream())

spotlessConfig(libs.versions.spotless.ktlint)

val newsApiKey = properties.getProperty("NEWS_API_KEY") ?: ""
val baseUrl = properties.getProperty("NEWS_API_BASE_URL") ?: ""

android {
    namespace = "com.michael.core.network"

    defaultConfig {
        buildConfigField(
            type = "String",
            name = "API_KEY",
            value = newsApiKey
        )

        buildConfigField(
            type = "String",
            name = "NEWS_API_BASE_URL",
            value = baseUrl
        )
    }
}

dependencies {
    implementation(project(":core:base"))
    implementation(project(":core:testing"))

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    api(libs.retrofit)
    api(libs.retrofit.gson)
    api(libs.logging.interceptor)
}
