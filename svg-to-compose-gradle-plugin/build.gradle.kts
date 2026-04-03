import com.vanniktech.maven.publish.GradlePlugin
import com.vanniktech.maven.publish.JavadocJar

plugins {
    `java-gradle-plugin`
    dev.tonholo.s2c.conventions.gradle.plugin
    alias(libs.plugins.dev.zacsweers.metro)
}

// Functional tests: end-to-end tests that exercise the plugin via GradleRunner.
val functionalTest: SourceSet = sourceSets.create("functionalTest")

val functionalTestTask = tasks.register<Test>("functionalTest") {
    description = "Runs functional tests against the plugin using Gradle TestKit."
    group = "verification"
    testClassesDirs = functionalTest.output.classesDirs
    classpath = functionalTest.runtimeClasspath
    useJUnitPlatform()
//    // Each test spawns a GradleRunner that loads AGP classes into the test JVM
//    // via buildscript classpath resolution. Running many tests exhausts Metaspace.
//    jvmArgs("-XX:MaxMetaspaceSize=4g")
}

tasks.check {
    dependsOn(functionalTestTask)
}

gradlePlugin {
    website.set("https://github.com/rafaeltonholo/svg-to-compose")
    vcsUrl.set("https://github.com/rafaeltonholo/svg-to-compose")
    plugins {
        create("svgToCompose") {
            id = group.toString()
            implementationClass = "dev.tonholo.s2c.gradle.Svg2ComposePlugin"
            displayName = "SVG/AVG to Compose"
            description = "Converts SVG and AVG to Android Jetpack Compose Icons."
        }
    }
    testSourceSets(functionalTest)
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xcontext-receivers")
    }

    sourceSets {
        test {
            resources.srcDirs(rootProject.layout.projectDirectory.dir("samples"))
        }
        named("functionalTest") {
            resources.srcDirs(rootProject.layout.projectDirectory.dir("samples"))
        }
    }
}

mavenPublishing {
    configure(
        GradlePlugin(
            // DGPv2 (Dokka 2.x) auto-attaches a javadoc JAR when the java plugin
            // is present. Using JavadocJar.Dokka(...) here would add a second one,
            // causing "multiple artifacts with identical extension and classifier"
            // on publish.
            javadocJar = JavadocJar.None(),
            sourcesJar = true,
        )
    )
    pom {
        name.set("SVG/XML to Compose Gradle Plugin")
        description.set(
            "A Gradle plugin that converts SVG or an Android Vector Drawable (AVG) to Android Jetpack Compose Icons.",
        )
    }
}

//dokka {
//    moduleName.set("SVG/XML to Compose Gradle Plugin")
//}

dependencies {
    compileOnly(libs.com.android.tools.build.gradle)
    implementation(libs.org.jetbrains.kotlin.gradle.plugin)
    implementation(libs.com.squareup.okio)
    implementation(projects.svgToCompose)
    testImplementation(kotlin("test"))
    "functionalTestImplementation"(libs.org.junit.jupiter)
    "functionalTestImplementation"(kotlin("test"))
    "functionalTestCompileOnly"(libs.com.android.tools.build.gradle)
    "functionalTestRuntimeOnly"("org.junit.platform:junit-platform-launcher")
}
