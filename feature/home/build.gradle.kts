plugins {
    `feature-module-config`
    alias(libs.plugins.hilt.plugin)
}

dependencies {
    implementation(project(":core:base"))
    implementation(project(":core:common"))
    implementation(project(":core:testing"))
    implementation(project(":core:ui"))

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
}

android {
    namespace = "com.michael.feature.home"
}
