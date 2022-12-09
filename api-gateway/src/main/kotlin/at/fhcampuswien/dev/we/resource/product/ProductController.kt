package at.fhcampuswien.dev.we.resource.product

import at.fhcampuswien.dev.we.order.model.product.ProductDTO
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.micronaut.validation.Validated
import javax.validation.Valid

@Secured(SecurityRule.IS_AUTHENTICATED)
@Validated
@Controller("/product")
class ProductController(private val productService: ProductService) {

    @Secured( "manager", "admin" )
    @Post("/create")
    fun createProduct(@Valid product: ProductDTO): HttpResponse<ProductDTO> {
        val productResponse = productService.createProduct(product)
        return HttpResponse.created(productResponse)
    }

    @Secured( "manager", "admin", "service" )
    @Get
    fun getAllProducts(): HttpResponse<List<ProductDTO>> {
        return HttpResponse.ok(productService.getAllProducts())
    }

    @Secured( "manager", "admin" )
    @Post("/block")
    fun blockProduct(@Valid product: ProductDTO): HttpResponse<ProductDTO> {
        val productResponse = productService.blockProduct(product)
        return HttpResponse.ok(productResponse)
    }
}
