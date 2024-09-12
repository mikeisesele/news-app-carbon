import utils.composeConfiguration
import utils.providetestDependencies

plugins {
    `core-module-config`
    alias(libs.plugins.hilt.plugin)
    alias(libs.plugins.kotlinCompose)
}

android {
    namespace = "com.michael.core.base"
    testFixtures {
        enable = true
    }
    composeConfiguration(libs.versions.compose.compiler)
}

dependencies {
    api(libs.core.ktx)
    api(libs.lifecycle.runtime.ktx)
    api(libs.lifecycle.viewmodel.ktx)
    api(libs.compose.runtime)
    api(libs.kotlinxSerializationJson)
    api(libs.hilt.navigation.compose)
    api(libs.navcompose)
    api(libs.easylog)


    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    detektPlugins(libs.detekt.formatter)
    providetestDependencies(libs)
}
