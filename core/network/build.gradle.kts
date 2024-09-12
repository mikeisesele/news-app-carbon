plugins {
    `core-module-config`
    alias(libs.plugins.hilt.plugin)
    alias(libs.plugins.kotlinSerialization)
}

android {
    namespace = "com.michael.core.network"
}

dependencies {
    implementation(project(":core:base"))
    implementation(project(":core:testing"))

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    api(libs.retrofit)
    api(libs.retrofit.gson)
    api(libs.logging.interceptor)
    api(libs.moshi)
}
