package tech.draeger

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ObjectConfigurationAction

class OkBroPlugin : Plugin<Project> {
    override fun apply(project: Project) {

        val extension = project.extensions.run {
            create("okBro", OkBroExtension::class.java)
        }

        project.run {

            apply { action: ObjectConfigurationAction ->
                action.plugin("org.jetbrains.kotlin.jvm")
                action.plugin("com.adarshr.test-logger")
                action.plugin("com.github.ben-manes.versions")
            }

            afterEvaluate {
                if (extension.enableDetekt) {
                    it.applyDetekt()
                }
            }

            tasks.run {
                create("heyBro", OkBroTask::class.java) {
                    it.group = "Development"
                    it.description = "say hello"
                }

                register("greeting") { task ->
                    task.doLast {
                        println("Hello from plugin 'tech.draeger.greeting'")
                    }
                }
            }
        }
    }

    private fun Project.applyDetekt() {
        apply { it.plugin("io.gitlab.arturbosch.detekt") }
        // TODO; how to configure detekt
        println("detekt is enabled")
    }
}
