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
