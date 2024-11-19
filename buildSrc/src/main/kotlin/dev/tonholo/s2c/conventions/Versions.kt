package dev.tonholo.s2c.conventions

import org.gradle.api.Project
import java.util.Properties

internal const val GROUP = "dev.tonholo.s2c"
internal val Project.Version: String
    get() {
        return rootProject.file("app.properties").reader().use {
            Properties().apply { load(it) }
        }["VERSION"].toString()
    }
