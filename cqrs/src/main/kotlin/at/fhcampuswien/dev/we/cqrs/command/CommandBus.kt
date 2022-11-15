package at.fhcampuswien.dev.we.cqrs.command

import at.fhcampuswien.dev.we.cqrs.Bus

interface CommandBus : Bus<Command>
