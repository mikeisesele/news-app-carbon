plugins {
    `core-module-config`
    alias(libs.plugins.hilt.plugin)
}

android {
    namespace = "com.michael.core.di"
}

dependencies {
    implementation(project(":core:base"))
    implementation(project(":core:common"))
    implementation(project(":core:network"))
    implementation(project(":core:localdata"))
    implementation(project(":core:securestore"))

    implementation(libs.hilt.android)
    implementation(libs.room.ktx)
    kapt(libs.hilt.android.compiler)
}
