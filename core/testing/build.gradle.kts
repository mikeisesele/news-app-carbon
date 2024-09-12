plugins {
    `core-module-config`
}

dependencies {
    implementation(project(":core:base"))
    implementation(libs.coroutines.test)
}

android {
    namespace = "com.michael.core.testing"
}
