package at.fhcampuswien.dev.we.domain.command

import at.fhcampuswien.dev.we.cqrs.command.Command

class FinishOrderCommand(val orderId: String) : Command
