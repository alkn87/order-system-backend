package at.fhcampuswien.dev.we.domain.query

import at.fhcampuswien.dev.we.cqrs.query.Query

class GetOrdersByStationQuery(val stationType: String) : Query
