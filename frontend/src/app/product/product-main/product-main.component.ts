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

  constructor(private socketService: WebSocketService, private productService: ProductService) {
  }

  ngOnDestroy(): void {
    this.socketService.messages.unsubscribe();
  }



  ngOnInit(): void {
    this.getProductList();

    this.socketService.messages.subscribe(msg => {
      if (msg.content === 'UPDATE') {
        this.getProductList();
      }
    });
  }

  private getProductList() {
    this.$productList = this.productService.getAllProducts();
  }


}
