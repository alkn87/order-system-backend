package at.fhcampuswien.dev.we.domain.command

import at.fhcampuswien.dev.we.cqrs.command.Command

class FinishStationOrderCommand(val stationOrderId: String) : Command
