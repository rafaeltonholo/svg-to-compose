package dev.tonholo.s2c.conventions.detekt

import dev.detekt.gradle.Detekt
import dev.detekt.gradle.report.ReportMergeTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.registering
import org.gradle.kotlin.dsl.withType

fun Project.registerDetektMergeReportsTask() {
    val mergeDetektReport: TaskProvider<ReportMergeTask> by tasks.registering(ReportMergeTask::class) {
        output.set(rootProject.layout.buildDirectory.file("reports/detekt/merged-report.xml"))
    }

    subprojects {
        tasks.withType<Detekt>().configureEach {
            finalizedBy(mergeDetektReport)
        }

        mergeDetektReport {
            input.from(
                tasks.withType<Detekt>().map {
                    it.reports.checkstyle.outputLocation
                },
            )
        }
    }
}
