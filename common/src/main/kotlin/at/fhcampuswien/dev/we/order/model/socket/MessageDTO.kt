package at.fhcampuswien.dev.we.order.model.socket

import io.micronaut.core.annotation.Introspected

@Introspected
class MessageDTO(val source: String, val content: Any)
