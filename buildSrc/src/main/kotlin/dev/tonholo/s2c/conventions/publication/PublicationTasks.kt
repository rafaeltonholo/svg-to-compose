package dev.tonholo.s2c.conventions.publication

import org.gradle.api.Project
import org.gradle.api.publish.maven.tasks.PublishToMavenRepository
import org.gradle.kotlin.dsl.withType

fun Project.registerPublishAllToMavenLocalTask() {
    tasks.create("publishAllToMavenLocal") {
        group = "publishing"
        description = "Publish all subprojects to maven local"
        doLast {
            println("After ${project.name}:publishToLocalMaven")
        }
        subprojects {
            tasks.withType<PublishToMavenRepository> {
                doLast {
                    println("After ${project.name}:publishAllPublicationsToTestMavenRepository")
                }
                this@create.dependsOn(this)
            }
        }
    }
}
