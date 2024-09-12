plugins {
    `core-module-config`
    alias(libs.plugins.hilt.plugin)
}

android {
    namespace = "com.michael.core.localdata"
}

dependencies {
    implementation(project(":core:base"))
    implementation(project(":core:models"))

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    api(libs.retrofit.gson)


    annotationProcessor(libs.room.compiler)
    api(libs.room.runtime)
    kapt(libs.kapt.room.compiler)
    api(libs.room.ktx)
}
