package utils

import gradle.kotlin.dsl.accessors._e8ac5e837605381d1e3b56097e86cd58.androidApis
import gradle.kotlin.dsl.accessors._e8ac5e837605381d1e3b56097e86cd58.androidTestImplementation
import gradle.kotlin.dsl.accessors._e8ac5e837605381d1e3b56097e86cd58.api
import gradle.kotlin.dsl.accessors._e8ac5e837605381d1e3b56097e86cd58.implementation
import gradle.kotlin.dsl.accessors._e8ac5e837605381d1e3b56097e86cd58.testApi
import gradle.kotlin.dsl.accessors._e8ac5e837605381d1e3b56097e86cd58.testImplementation
import gradle.kotlin.dsl.accessors._e8ac5e837605381d1e3b56097e86cd58.testRuntimeOnly
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.kotlin.dsl.DependencyHandlerScope

fun DependencyHandlerScope.providetestDependencies(libs: LibrariesForLibs) {
    with(this) {
        testApi(libs.junit)
        testRuntimeOnly(libs.junit.engine)
        api(libs.androidx.junit.ktx)
        testApi(libs.turbine)
        testApi(libs.mockk)
        testApi(libs.androidx.core.testing)
        testApi(libs.coroutines.test)
        testApi(libs.kotest.assertions)
    }
}