import dev.tonholo.s2c.conventions.AppProperties
import dev.tonholo.s2c.conventions.kmp.targets.useJvm
import dev.tonholo.s2c.conventions.kmp.targets.useNative
import org.jetbrains.kotlin.gradle.InternalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.targets.jvm.tasks.KotlinJvmRun

plugins {
    id("dev.tonholo.s2c.conventions.common")
    org.jetbrains.kotlin.multiplatform
    com.github.gmazzo.buildconfig
    id("dev.tonholo.s2c.conventions.publication")
}

kotlin {
    useNative()
    useJvm()

    compilerOptions {
        freeCompilerArgs.add("-Xwhen-guards")
    }
}

buildConfig {
    packageName(group.toString().plus(".config"))
    useKotlinOutput()
    AppProperties.forEach { (name, value) ->
        buildConfigField(name, value.toString())
    }
}

@OptIn(InternalKotlinGradlePluginApi::class)
tasks.withType<KotlinJvmRun> {
    isIgnoreExitValue = true
}
