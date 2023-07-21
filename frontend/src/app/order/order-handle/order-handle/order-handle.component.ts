import { Component, OnDestroy, OnInit } from '@angular/core';
import { OrderService } from '../../../core/services/order.service';
import { Subject } from 'rxjs/internal/Subject';
import { OrderDto } from '../../../core/model/order/order.dto';
import { OrderStatusDto } from '../../../core/model/order/order-status.dto';
import { ActivatedRoute, Router } from '@angular/router';
import { BillingStateService } from '../../../core/services/billing-state.service';
import { UpdateEventService } from '../../../core/services/update-event.service';
import { OrderBillingDto } from '../../../core/model/order/oder-billing.dto';

@Component({
  selector: 'app-order-handle',
  templateUrl: './order-handle.component.html',
  styleUrls: ['./order-handle.component.scss']
})
export class OrderHandleComponent implements OnInit, OnDestroy {

  $ordersCreatedSubject: Subject<OrderDto[]> = new Subject<OrderDto[]>();
  $ordersDeliveredSubject: Subject<OrderBillingDto[]> = new Subject<OrderBillingDto[]>();

  constructor(private orderService: OrderService,
              private updateEventService: UpdateEventService,
              private billingStateService: BillingStateService,
              private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.getOrders();
    this.getOrdersForBilling();
    this.updateEventService.getServerSentEvent()
      .subscribe({
        next: (data: MessageEvent) => {
          console.log('SSE received');
          console.log(JSON.parse(data.data)['data']);
          if (JSON.parse(data.data)['data'] === 'UPDATE') {
            this.getOrders();
            this.getOrdersForBilling();
          }
        },
        error: () => console.error(`Error: ${this}`)
      });
  }

  ngOnDestroy(): void {
    this.$ordersCreatedSubject.unsubscribe();
    this.$ordersDeliveredSubject.unsubscribe();
    this.updateEventService.closeEventSource();
  }

  private getOrders() {
    this.orderService.getOrders().subscribe(response => {
      const ordersCreated = response.filter(order => order.orderStatus === OrderStatusDto.CREATED);

      this.$ordersCreatedSubject.next(ordersCreated);
    });
  }

  private getOrdersForBilling() {
    this.orderService.getOrdersForBilling().subscribe(response => {
      this.$ordersDeliveredSubject.next(response);
    });
  }


  navigateToBilling(order: OrderBillingDto) {
    if (order.deliverTo !== undefined) {
      this.billingStateService.setOrderForBilling(order);
      this.router.navigate(['billing', order.deliverTo], {relativeTo: this.route});
    }

  }
}
