package utils

import com.diffplug.gradle.spotless.SpotlessExtension
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.configure

fun Project.spotlessConfig(ktLintVersion: Provider<String>) {
    configure<SpotlessExtension> {
//        // Allow spotless to run on git diff instead of entire project
//        ratchetFrom("origin/${BuildConstants.baseBranch}")
        kotlin {
            ktlint(ktLintVersion.get())
                .editorConfigOverride(
                    mapOf(
                        Pair("disabled_rules", "filename"),
                        Pair("ij_kotlin_allow_trailing_comma", true),
                        Pair("ij_kotlin_allow_trailing_comma_on_call_site", true)
                    )
                )
            target("**/*.kt")
        }
        kotlinGradle {
            ktlint(ktLintVersion.get())
            target("*.gradle.kts")
        }
    }
}
