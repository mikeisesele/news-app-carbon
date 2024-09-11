package utils


import gradle.kotlin.dsl.accessors._7e497ce5ffc7b9cdcfaba95c60bcd8e4.api
import gradle.kotlin.dsl.accessors._7e497ce5ffc7b9cdcfaba95c60bcd8e4.testApi
import gradle.kotlin.dsl.accessors._7e497ce5ffc7b9cdcfaba95c60bcd8e4.testRuntimeOnly
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