plugins {
    `core-module-config`
    alias(libs.plugins.hilt.plugin)
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
    api(libs.retrofit.moshi)
    api(libs.logging.interceptor)
    api(libs.moshi)
}
