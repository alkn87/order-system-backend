package at.fhcampuswien.dev.we.domain.event

import at.fhcampuswien.dev.we.cqrs.event.Event

data class ProductDeletedEvent(val name: String) : Event
