import dev.detekt.gradle.Detekt
import dev.detekt.gradle.DetektCreateBaselineTask
import dev.tonholo.s2c.conventions.detekt.DetektConfig
import dev.tonholo.s2c.conventions.libs

plugins {
    dev.detekt
}

detekt {
    autoCorrect = true
    buildUponDefaultConfig = true // preconfigure defaults
    allRules = false // activate all available (even unstable) rules.
    // point to your custom config defining rules to run, overwriting default behavior
    config.setFrom("${rootProject.rootDir}/config/detekt.yml")
}

dependencies {
    detektPlugins(libs.detekt.rules.ktlint.wrapper)
}

tasks.withType<Detekt>().configureEach {
    reports {
        checkstyle.required.set(true)
        html.required.set(true)
        sarif.required.set(true)
        markdown.required.set(true)
    }
}

tasks.withType<Detekt>().configureEach {
    jvmTarget = DetektConfig.javaVersion
    exclude {
        DetektConfig.excludedDirs.any { dir ->
            dir in it.file.absolutePath
        }
    }
}

tasks.withType<DetektCreateBaselineTask>().configureEach {
    jvmTarget = DetektConfig.javaVersion
}
