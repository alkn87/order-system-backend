import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BillingStateService } from '../../../core/services/billing-state.service';
import { OrderDto } from '../../../core/model/order/order.dto';
import { BehaviorSubject } from 'rxjs';
import { OrderService } from '../../../core/services/order.service';
import { OrderItemDto } from '../../../core/model/order/order-item.dto';
import { OrderStatusDto } from '../../../core/model/order/order-status.dto';
import { Subject } from 'rxjs/internal/Subject';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-order-billing',
  templateUrl: './order-billing.component.html',
  styleUrls: ['./order-billing.component.scss']
})
export class OrderBillingComponent implements OnInit {

  totalItems: OrderItemDto[] = [];
  interimItems: OrderItemDto[] = [];
  totalAmount: number = 0;

  $orderSubject: BehaviorSubject<OrderDto> = new BehaviorSubject<OrderDto>({deliverTo: '', orderAgent: '', orderItems: []});
  $interimItemsSubject: Subject<OrderItemDto[]> = new Subject<OrderItemDto[]>();
  $orderInterimTotalSubject: Subject<number> = new Subject<number>();
  $totalItemsSubject: Subject<OrderItemDto[]> = new Subject<OrderItemDto[]>();
  $orderTotalSubject: Subject<number> = new Subject<number>();

  constructor(private route: ActivatedRoute,
              private router: Router,
              private billingStateService: BillingStateService,
              private orderService: OrderService,
              private toastService: ToastrService) {
  }

  ngOnInit(): void {
    this.$orderSubject.subscribe(value => {
      this.totalItems = value.orderItems;
      this.$totalItemsSubject.next(value.orderItems);
    });

    this.$totalItemsSubject.subscribe(value => {
      this.calculateOrderTotal();
    });

    this.route.params.subscribe(params => {
      this.$orderSubject.next(this.billingStateService.orderForBilling);
      // this.$orderSubject.next({
      //   deliverTo: 'T1', orderAgent: 'test user', orderItems: [
      //     {productName: 'Burger', quantity: 2, productPrice: 3.90, productType: 'FOOD'} as OrderItemDto,
      //     {productName: 'Salad', quantity: 3, productPrice: 4.20, productType: 'FOOD'} as OrderItemDto,
      //     {productName: 'Beer', quantity: 2, productPrice: 3.80, productType: 'DRINK'} as OrderItemDto
      //   ], id: 'aaw3aygs223w3', orderStatus: OrderStatusDto.DELIVERED
      // } as OrderDto);
    });
  }

  addToInterimItems(orderItem: OrderItemDto) {
    const index = this.interimItems.findIndex(item => item.productName === orderItem.productName);
    if (index > -1) {
      this.interimItems[index].quantity++;
    } else {
      this.interimItems.push({
        productName: orderItem.productName,
        productPrice: orderItem.productPrice,
        productType: orderItem.productType,
        quantity: 1
      })
    }
    this.$interimItemsSubject.next(this.interimItems);
  }

  // addToTotalItems(orderItem: OrderItemDto) {
  //   const index = this.totalItems.findIndex(item => item.productName === orderItem.productName);
  //   if (index > -1) {
  //     this.totalItems[index].quantity++;
  //   } else {
  //     this.totalItems.push({
  //       productName: orderItem.productName,
  //       productPrice: orderItem.productPrice,
  //       productType: orderItem.productType,
  //       quantity: 1
  //     })
  //   }
  //   this.$interimItemsSubject.next(this.interimItems);
  // }
  //
  // removeFromInterimItems(orderItem: OrderItemDto) {
  //   const index = this.interimItems.findIndex(item => item.productName === orderItem.productName);
  //   if (index > -1) {
  //     this.interimItems[index].quantity > 1 ? this.interimItems[index].quantity-- : this.interimItems.splice(index, 1);
  //     this.$interimItemsSubject.next(this.interimItems);
  //   }
  // }

  removeFromTotalItems(orderItem: OrderItemDto) {
    const index = this.totalItems.findIndex(item => item.productName === orderItem.productName);
    if (index > -1) {
      this.totalItems[index].quantity > 1 ? this.totalItems[index].quantity-- : this.totalItems.splice(index, 1);
      this.$totalItemsSubject.next(this.totalItems);
    }
  }


  handlePartialPayment(item: OrderItemDto) {
    this.addToInterimItems(item);
    this.removeFromTotalItems(item);
    this.calculateOrderTotal();
  }

  clearInterimItems() {
    this.interimItems = [];
    this.$interimItemsSubject.next(this.interimItems);
    this.$orderInterimTotalSubject.next(0);
  }

  submitOrderFinish() {
    if (this.billingStateService.orderForBilling.id !== undefined) {
      this.orderService.billOrder(this.billingStateService.orderForBilling.id).subscribe(response => {
        this.toastService.success('Order with ID ' + response + ' finished.');
        this.router.navigate(['order']);
      });
    }
  }

  private calculateOrderTotal() {
    this.$orderTotalSubject.next(this.totalItems.reduce((acc, res) => acc + res.quantity * res.productPrice, 0));
    this.totalAmount = this.totalItems.reduce((acc, res) => acc + res.quantity * res.productPrice, 0);
    this.$orderInterimTotalSubject.next(this.interimItems.reduce((acc, res) => acc + res.quantity * res.productPrice, 0));
  }
}
