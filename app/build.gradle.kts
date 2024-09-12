import utils.providetestDependencies

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    `app-module-config`
    alias(libs.plugins.hilt.plugin)
    alias(libs.plugins.kotlinCompose)
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

dependencies {

    implementation(project(":core:base"))
    implementation(project(":core:common"))
    implementation(project(":core:network"))
    implementation(project(":core:ui"))
    implementation(project(":feature:news"))
    testImplementation(project(":core:testing"))

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(platform(libs.compose.bom))
    implementation(libs.easylog)

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)

    providetestDependencies(libs)
}
