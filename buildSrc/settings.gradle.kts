/**
 * Sharing the version catalog toml file between the modules is done with this script
 * This config generates LibraryForLibs used in buildSrc/build.gradle.kts
 * read more https://docs.gradle.org/current/userguide/platforms.html#sec:sharing-catalogs
 */
dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}
