package at.fhcampuswien.dev.we.cqrs.event

import at.fhcampuswien.dev.we.cqrs.Bus

interface EventBus : Bus<Event>
