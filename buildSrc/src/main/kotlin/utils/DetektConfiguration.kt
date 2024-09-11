package utils

import org.gradle.api.Project
import org.gradle.api.tasks.JavaExec
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.task
import java.io.ByteArrayOutputStream
import java.io.File
import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

fun JavaExec.getDiffedFilesFromBranch(branch: String, project: Project): Pair<String, Int> {
    val outputStream = ByteArrayOutputStream()
    val path = project.rootDir.absolutePath
    val cmd = "cd $path; git diff --diff-filter=d --name-only origin/$branch --relative"
    project.exec {
        executable = "sh"
        standardOutput = outputStream
        args = listOf("-c", cmd)
    }
    val output = outputStream.toString().trim()
    if (output.isEmpty()) return "" to 0
    // get comma separated string of files
    var fileCount: Int
    return output
        .split("\n")
        // Filter kotlin files
        .filter { it.matches(Regex(".*(\\.kt)(s)*")) }
        .also {
            fileCount = it.size
        }
        .joinToString(",") {
            "$path/$it"
        } to fileCount
}

// We only need to register it once
fun Project.registerIncrementalDetektTask(version: String) = task<JavaExec>("incrementalDetekt") {
    group = "CI Detekt Checks"
    description = "Run Kotlin static analysis on changed files."
    classpath = configurations["detekt"]
    mainClass.set("io.gitlab.arturbosch.detekt.cli.Main")
    val file = File(project.buildDir, "detekt-formatting.jar")
    doFirst {
        // Download the formatter plugin
        // https://github.com/detekt/detekt/discussions/4790
        if (!file.exists()) {
            val url = "https://github.com/detekt/detekt/releases/download/v" +
                "$version/detekt-formatting-$version.jar"
            file.parentFile.mkdirs()
            println("Downloading file : $file")
            URL(url).openStream().use {
                Files.copy(it, Paths.get(file.absolutePath), StandardCopyOption.REPLACE_EXISTING)
            }
        }
    }
    doFirst {
        var (changedFiles, count) = this@task.getDiffedFilesFromBranch(
            branch = BuildConstants.baseBranch,
            project = project
        )
        if (count == 0) {
            println("No kotlin files changed! Skipping task...")
            // forces detekt to ignore all files
            changedFiles = "$rootDir/gradle"
        } else {
            println("Running detekt on $count file(s)")
        }

        val params = listOf(
            "-i", changedFiles,
            "-c", "$rootDir/config/detekt/detekt.yml",
            "-p", file.absolutePath
        )
        args(params)
    }
}
