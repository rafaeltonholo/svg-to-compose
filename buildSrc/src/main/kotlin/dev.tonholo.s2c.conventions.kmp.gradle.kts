import dev.tonholo.s2c.conventions.kmp.targets.useJvm
import dev.tonholo.s2c.conventions.kmp.targets.useNative
import org.jetbrains.kotlin.gradle.InternalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.targets.jvm.tasks.KotlinJvmRun

plugins {
    id("dev.tonholo.s2c.conventions.common")
    org.jetbrains.kotlin.multiplatform
    com.github.gmazzo.buildconfig
}

kotlin {
    useNative()
    useJvm()
}

buildConfig {
    packageName(group.toString().plus(".config"))
    useKotlinOutput()
    val envFile = File("${rootDir.absolutePath}/app.properties")
    val env = mutableListOf<Pair<String, String>>()
    envFile.forEachLine { line ->
        val (name, value) = line.split("=")
        env += name to value
    }
    env.forEach { (name, value) ->
        buildConfigField(name, value)
    }
}

@OptIn(InternalKotlinGradlePluginApi::class)
tasks.withType<KotlinJvmRun> {
    isIgnoreExitValue = true
}
