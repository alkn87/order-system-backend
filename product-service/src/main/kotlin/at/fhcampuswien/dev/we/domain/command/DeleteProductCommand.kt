package at.fhcampuswien.dev.we.domain.command

import at.fhcampuswien.dev.we.cqrs.command.Command
import at.fhcampuswien.dev.we.order.model.product.ProductDTO

data class DeleteProductCommand(val productDTO: ProductDTO) : Command
