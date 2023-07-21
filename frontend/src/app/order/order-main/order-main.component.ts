import { Component, OnDestroy, OnInit } from '@angular/core';
import { ProductService } from '../../core/services/product.service';
import { map, Observable, of } from 'rxjs';
import { ProductDto } from '../../core/model/product/product.dto';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { OrderItemDto } from '../../core/model/order/order-item.dto';
import { OrderDto } from '../../core/model/order/order.dto';
import { KeycloakService } from 'keycloak-angular';
import { Subject } from 'rxjs/internal/Subject';
import { OrderService } from '../../core/services/order.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-order-main',
  templateUrl: './order-main.component.html',
  styleUrls: ['./order-main.component.scss']
})
export class OrderMainComponent implements OnInit, OnDestroy {

  orderItemList: OrderItemDto[] = []
  orderForm: FormGroup = new FormGroup({});
  orderTotal: number = 0;
  orderAgentName: string = '';

  $orderTotalSubject: Subject<number> = new Subject<number>();
  $orderItemListSubject: Subject<OrderItemDto[]> = new Subject<OrderItemDto[]>();

  $productList: Observable<ProductDto[]> = of([]);
  $productGroup: Observable<string[]> = of([]);

  constructor(private productService: ProductService,
              private formBuilder: FormBuilder,
              private keycloakService: KeycloakService,
              private orderService: OrderService,
              private toastService: ToastrService) {
  }

  ngOnDestroy(): void {
    this.$orderTotalSubject.unsubscribe();
    this.$orderItemListSubject.unsubscribe();
  }

  ngOnInit(): void {
    this.orderForm = this.formBuilder.group(
      {
        deliverTo: this.formBuilder.control('', Validators.required),
        commentFood: this.formBuilder.control(''),
        commentDrink: this.formBuilder.control(''),
      }
    );

    this.getProductList();
    this.$productList.subscribe(value => {
      this.buildProductGroups(value);
      value.forEach((product) => {
        this.orderForm.addControl(product.productName, this.formBuilder.control(0, Validators.min(0)))
      })
    });

    this.$orderItemListSubject.subscribe(_ => this.calculateOrderTotal());

    this.keycloakService.loadUserProfile().then(profile => this.orderAgentName = profile.username ? profile.username : '');
  }

  getProductList() {
    this.$productList = this.productService.getAllProducts().pipe(
      map(products => products.sort((a, b) => a.productName.localeCompare(b.productName)))
    );
  }

  addProductToOrderItems(product: ProductDto) {
    const index = this.orderItemList.findIndex(item => item.productName === product.productName);
    if (index > -1) {
      this.orderItemList[index].quantity++;
    } else {
      this.orderItemList.push({
        productName: product.productName,
        productPrice: product.productPrice,
        productType: product.productType,
        quantity: 1
      })
    }
    this.$orderItemListSubject.next(this.orderItemList);
  }

  removeProductFromOderItems(product: ProductDto) {
    const index = this.orderItemList.findIndex(item => item.productName === product.productName);
    if (index > -1) {
      this.orderItemList[index].quantity > 1 ? this.orderItemList[index].quantity-- : this.orderItemList.splice(index, 1);
      this.$orderItemListSubject.next(this.orderItemList);
    }
  }

  sendOrder() {
    let order: OrderDto = {
      orderAgent: this.keycloakService.getUsername(),
      deliverTo: this.orderForm.controls['deliverTo'].value,
      commentFood: this.orderForm.controls['commentFood'].value,
      commentDrink: this.orderForm.controls['commentDrink'].value,
      orderItems: this.orderItemList
    }
    this.orderService.createOrder(order).subscribe(_ => {
      this.toastService.success('Order for ' + order.deliverTo + ' was created.');
      this.resetOrder();
    });
  }

  resetOrder() {
    this.orderForm.reset();
    this.orderItemList = [];
    this.$orderItemListSubject.next(this.orderItemList);
  }

  isValidOrder(): boolean {
    return this.orderForm.valid && this.orderItemList.length > 0;
  }

  private calculateOrderTotal() {
    this.orderTotal = this.orderItemList.reduce((acc, res) => acc + res.quantity * res.productPrice, 0);
    this.$orderTotalSubject.next(this.orderItemList.reduce((acc, res) => acc + res.quantity * res.productPrice, 0));
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
