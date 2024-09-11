import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.kotlin.dsl.`kotlin-dsl`

val libs = the<LibrariesForLibs>()

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
    id("com.diffplug.spotless") version libs.versions.plugin.spotless.get() apply false
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}


/**
 * NOTE: Required to resolve dependency conflict with java poet. AGP is pulling java poet 1.10.0 but
 * hilt requires java poet 1.13.0.
 * Ref: https://github.com/google/dagger/issues/3068
 */
configurations.all {
    resolutionStrategy.eachDependency {
        when (requested.name) {
            "javapoet" -> useVersion("1.13.0")
        }
    }
}

dependencies {
    /**
     * workaround to make version catalogs accessible to precompiled script plugins
     * https://github.com/gradle/gradle/issues/15383#issuecomment-779893192
     */
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
    implementation(libs.android.gradle)
    implementation(libs.kotlin.gradle)
    implementation(libs.detekt.plugin)
    implementation(plugin(id = "com.diffplug.spotless", version = libs.versions.plugin.spotless.get()))
}

// https://docs.gradle.org/current/userguide/plugins.html?_ga=2.189164651.1386885244.1660553312-166908490.1647955116#sec:plugin_markers
/**
 * Returns the plugin marker artifact for the specified plugin Id and Version
 * @param id the id of the plugin (ex: kotlin-android)
 * @param version the plugin version (ex: 1.0.0)
 */
fun plugin(id: String, version: String): String {
    return "$id:$id.gradle.plugin:$version"
}
