plugins {
    `core-module-config`
}

dependencies {
    implementation(project(":core:base"))
    implementation(libs.coroutines.test)
    detektPlugins(libs.detekt.formatter)
}

android {
    namespace = "com.michael.core.testing"
}
