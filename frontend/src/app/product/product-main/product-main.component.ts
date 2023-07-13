import { Component, OnDestroy, OnInit } from '@angular/core';
import { ProductDto } from '../../core/model/product/product.dto';
import { ProductService } from '../../core/services/product.service';
import { Observable, of } from 'rxjs';
import { UpdateEventService } from '../../core/services/update-event.service';
import { SalesEntryDto } from '../../core/model/sales/sales-entry.dto';

@Component({
  selector: 'app-product-main',
  templateUrl: './product-main.component.html',
  styleUrls: ['./product-main.component.scss']
})
export class ProductMainComponent implements OnInit, OnDestroy {

  $productList: Observable<ProductDto[]> = of([]);
  $totalRevenue: Observable<number> = of(0);
  $totalSales: SalesEntryDto = {};
  $productGroup: Observable<string[]> = of([]);

  constructor(private productService: ProductService,
              private updateEventService: UpdateEventService) {
  }

  ngOnDestroy(): void {
    this.updateEventService.closeEventSource();
  }

  ngOnInit(): void {
    this.getProductList();
    this.getTotalRevenue();
    this.getTotalSales();
    this.$productList.subscribe(value => {
      this.buildProductGroups(value);
    });

    this.updateEventService.getServerSentEvent()
      .subscribe({
        next: (data: MessageEvent) => {
          console.log('SSE received');
          console.log(JSON.parse(data.data)['data']);
          if (JSON.parse(data.data)['data'] === 'UPDATE') {
            this.getProductList();
            this.getTotalRevenue();
            this.getTotalSales();
          }
        },
        error: () => console.error(`Error: ${this}`)
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

  private getTotalRevenue() {
    this.$totalRevenue = this.productService.getTotalRevenue();
  }

  private getTotalSales() {
    this.productService.getTotalSales().subscribe(data => {
      this.$totalSales = data;
      console.log(this.$totalSales)
    });
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
