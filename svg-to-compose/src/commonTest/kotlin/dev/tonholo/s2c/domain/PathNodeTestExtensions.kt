package dev.tonholo.s2c.domain

import dev.tonholo.s2c.emitter.imagevector.PathNodeEmitter

internal fun PathNodes.emit(): String = PathNodeEmitter().emit(this)
