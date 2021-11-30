package tech.draeger

import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import java.io.File

fun createTestBuild(
    buildGradle: String,
    settingsGradle: String = "",
    projectDir: String = "build/functionalTest",
): ProjectDir = File(projectDir).also {
    it.mkdirs()
    it.resolve("settings.gradle").writeText(settingsGradle)
    it.resolve("build.gradle").writeText(buildGradle)
}

typealias ProjectDir = File

fun ProjectDir.runTask(task: String): BuildResult {
    return with(GradleRunner.create()) {
        forwardOutput()
        withPluginClasspath()
        withArguments(task)
        withProjectDir(this@runTask)
        build()
    }
}
