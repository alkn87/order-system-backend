package at.fhcampuswien.dev.we.cqrs.query

import at.fhcampuswien.dev.we.cqrs.Bus

interface QueryBus : Bus<Query>
