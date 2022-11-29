package at.fhcampuswien.dev.we.domain.command

import at.fhcampuswien.dev.we.cqrs.command.Command

data class CreateProductCommand(val productName: String, val productPrice: Double, val productType: String) : Command
