import dev.tonholo.s2c.conventions.detekt.registerDetektMergeReportsTask
import dev.tonholo.s2c.conventions.publication.registerPublishAllToMavenLocalTask

plugins {
    dev.tonholo.s2c.conventions.dokka
}

registerDetektMergeReportsTask()
registerPublishAllToMavenLocalTask()
