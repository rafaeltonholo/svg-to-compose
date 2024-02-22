plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.kotlinMultiplatform) apply false
//    alias(libs.plugins.io.gitlab.arturbosch.detekt)
}

buildscript {
    dependencies {
        classpath(libs.com.codingfeline.buildkonfig.gradle.plugin)
    }
}

//detekt {
//    autoCorrect = true
//    buildUponDefaultConfig = true // preconfigure defaults
//    allRules = false // activate all available (even unstable) rules.
//    config.setFrom("$projectDir/config/detekt.yml") // point to your custom config defining rules to run, overwriting default behavior
//    source.setFrom(
//        "shared/src/commonMain/kotlin",
//        "shared/src/nativeMain/kotlin",
//    )
//}

//tasks.withType<Detekt>().configureEach {
//    reports {
//        html.required.set(true) // observe findings in your browser with structure and code snippets
//        xml.required.set(true) // checkstyle like format mainly for integrations like Jenkins
//        txt.required.set(true) // similar to the console output, contains issue signature to manually edit baseline files
//        sarif.required.set(true) // standardized SARIF format (https://sarifweb.azurewebsites.net/) to support integrations with GitHub Code Scanning
//        md.required.set(true) // simple Markdown format
//    }
//}
//
//tasks.withType<Detekt>().configureEach {
//    jvmTarget = "1.8"
//}
//tasks.withType<DetektCreateBaselineTask>().configureEach {
//    jvmTarget = "1.8"
//}
