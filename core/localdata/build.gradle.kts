plugins {
    `core-module-config`
    alias(libs.plugins.hilt.plugin)
}

android {
    namespace = "com.michael.core.localdata"
}

dependencies {
    implementation(project(":core:base"))

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    annotationProcessor(libs.room.compiler)
    implementation(libs.room.runtime)
    kapt(libs.kapt.room.compiler)
    implementation(libs.room.ktx)
}
