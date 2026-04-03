package dev.tonholo.s2c.conventions.publication

import org.gradle.api.Project
import org.gradle.api.publish.maven.tasks.PublishToMavenLocal
import org.gradle.kotlin.dsl.withType

fun Project.registerPublishAllToMavenLocalTask() {
    val publishTask = tasks.register("publishAllToMavenLocal") {
        group = "publishing"
        description = "Publish all subprojects to Maven Local (~/.m2)"
    }
    subprojects {
        afterEvaluate {
            tasks.withType<PublishToMavenLocal>().all {
                publishTask.configure { dependsOn(this@all) }
            }
        }
    }
}
