package tech.draeger

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

open class OkBroTask : DefaultTask() {

    @TaskAction
    fun action() {
        println("Hello from task")
    }

}
