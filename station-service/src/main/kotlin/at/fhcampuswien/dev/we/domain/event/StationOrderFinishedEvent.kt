package at.fhcampuswien.dev.we.domain.event

import at.fhcampuswien.dev.we.cqrs.event.Event

class StationOrderFinishedEvent(val orderId: String) : Event
