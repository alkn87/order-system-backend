package at.fhcampuswien.dev.we.domain.handler

import at.fhcampuswien.dev.we.cqrs.query.QueryHandler
import at.fhcampuswien.dev.we.domain.query.GetAllProductsQuery
import at.fhcampuswien.dev.we.order.model.product.ProductDTO
import at.fhcampuswien.dev.we.repository.ProductRepository
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
open class GetAllProductsQueryHandler(private val repository: ProductRepository) :
    QueryHandler<GetAllProductsQuery, List<ProductDTO>> {

    private val logger: Logger = LoggerFactory.getLogger(GetAllProductsQueryHandler::class.java)

    override fun handle(query: GetAllProductsQuery): List<ProductDTO> {
        val result = repository.findAll().toList().map { ProductDTO(it.productName, it.productPrice, it.productType, it.productStatus) }
        logger.info("handled query!")
        return result
    }
}
