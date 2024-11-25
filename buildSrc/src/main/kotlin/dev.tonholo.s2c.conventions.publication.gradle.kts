import com.vanniktech.maven.publish.SonatypeHost

plugins {
    com.vanniktech.maven.publish
}

publishing {
    repositories {
        maven {
            name = "testMaven"
            url = uri(rootProject.layout.buildDirectory.dir("localMaven"))
        }
    }
}

mavenPublishing {
    coordinates(
        groupId = group.toString(),
        artifactId = project.name,
        version = version.toString(),
    )
    pom {
        inceptionYear.set("2024")
        url.set("https://github.com/rafaeltonholo/svg-to-compose")

        licenses {
            license {
                name.set("MIT")
                url.set("https://opensource.org/licenses/MIT")
            }
        }

        developers {
            developer {
                id.set("rafaeltonholo")
                name.set("Rafael Tonholo")
                email.set("rafael@tonholo.dev")
            }
        }

        scm {
            url.set("https://github.com/rafaeltonholo/svg-to-compose")
            connection.set("scm:git:git://github.com/rafaeltonholo/svg-to-compose.git")
            developerConnection.set("scm:git:ssh://git@github.com/rafaeltonholo/svg-to-compose.git")
        }
    }

    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    signAllPublications()
}
