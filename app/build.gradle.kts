import utils.providetestDependencies

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    `app-module-config`
    alias(libs.plugins.hilt.plugin)
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

dependencies {

    implementation(project(":core:ui"))
    implementation(project(":core:common"))
    api(project(":core:base"))
    implementation(project(":core:securestore"))
    testImplementation(project(":core:testing"))
    implementation(project(":feature:home"))

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.navigation)

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)

    providetestDependencies(libs)
    detektPlugins(libs.detekt.formatter)
}
