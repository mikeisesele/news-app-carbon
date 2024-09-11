import utils.composeConfiguration

plugins {
    `core-module-config`
    alias(libs.plugins.hilt.plugin)
    alias(libs.plugins.kotlinCompose)
}

android {
    namespace = "com.michael.core.common"
    testFixtures {
        enable = true
    }
    composeConfiguration(libs.versions.compose.compiler)
}

dependencies {
    implementation(project(":core:base"))

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    api(libs.compose.runtime)
}
