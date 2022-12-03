package at.fhcampuswien.dev.we.resource.product

import at.fhcampuswien.dev.we.order.model.product.ProductDTO
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import javax.validation.Valid

@Validated
@Controller("/product")
class ProductController(private val productService: ProductService) {

    @Post("/create")
    fun createProduct(@Valid product: ProductDTO): HttpResponse<ProductDTO> {
        val productResponse = productService.createProduct(product)
        return HttpResponse.created(productResponse)
    }

    @Get
    fun getAllProducts(): HttpResponse<List<ProductDTO>> {
        return HttpResponse.ok(productService.getAllProducts())
    }
}
