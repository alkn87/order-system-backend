package at.fhcampuswien.dev.we.domain.event

import at.fhcampuswien.dev.we.cqrs.event.Event

data class ProductCreatedEvent(val name: String) : Event
