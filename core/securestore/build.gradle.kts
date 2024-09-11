plugins {
    `core-module-config`
    alias(libs.plugins.hilt.plugin)
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.michael.core.securestore"
}

dependencies {
    implementation(project(":core:base"))
    api(libs.security.crypto)
    api(libs.biometric)

    implementation(libs.firebase.crashlytics)
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
}
