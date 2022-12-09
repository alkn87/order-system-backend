import { Component, OnDestroy, OnInit } from '@angular/core';
import { WebSocketService } from '../../core/services/web-socket.service';
import { ProductDto } from '../../core/model/product/product.dto';
import { ProductService } from '../../core/services/product.service';
import { Observable, of } from 'rxjs';

@Component({
  selector: 'app-product-main',
  templateUrl: './product-main.component.html',
  styleUrls: ['./product-main.component.scss']
})
export class ProductMainComponent implements OnInit, OnDestroy {

  $productList: Observable<ProductDto[]> = of([]);
  $productGroup: Observable<string[]> = of([]);

  constructor(private socketService: WebSocketService, private productService: ProductService) {
  }

  ngOnDestroy(): void {
    this.socketService.messages.unsubscribe();
  }

  ngOnInit(): void {
    this.getProductList();
    this.$productList.subscribe(value => {
      this.buildProductGroups(value);
    });
    this.socketService.messages.subscribe(msg => {
      if (msg.content === 'UPDATE') {
        this.getProductList();
      }
    });
  }

  blockProduct(product: ProductDto) {
    this.productService.blockProduct(product).subscribe(response => {
      console.log(response);
    });
  }

  private getProductList() {
    this.$productList = this.productService.getAllProducts();
  }

  private buildProductGroups(products: ProductDto[]) {
    const result = this.groupBy(products, 'productType');
    this.$productGroup = of(Object.keys(result));
  }

  private groupBy(array: any[], property: string | number) {
    return array.reduce((grouped, element) => ({
      ...grouped,
      [element[property]]: [...(grouped[element[property]] || []), element]
    }), {})
  }
}
