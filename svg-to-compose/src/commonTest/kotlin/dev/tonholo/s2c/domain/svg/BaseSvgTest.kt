package dev.tonholo.s2c.domain.svg

import dev.tonholo.s2c.domain.xml.XmlRootNode
import dev.tonholo.s2c.logger.Logger
import dev.tonholo.s2c.logger.NoOpLogger

abstract class BaseSvgTest {
    val logger: Logger = NoOpLogger
    val root = SvgRootNode(
        parent = XmlRootNode(children = mutableSetOf()),
        children = mutableSetOf(),
        attributes = mutableMapOf(),
    )
}
