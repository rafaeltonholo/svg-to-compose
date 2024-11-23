import dev.tonholo.s2c.conventions.AppProperties

plugins {
    id("dev.tonholo.s2c.conventions.detekt")
}

AppProperties.init(rootProject)

group = AppProperties.group
version = AppProperties.version
