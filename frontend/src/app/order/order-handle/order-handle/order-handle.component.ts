import { Component, OnDestroy, OnInit } from '@angular/core';
import { OrderService } from '../../../core/services/order.service';
import { WebSocketService } from '../../../core/services/web-socket.service';
import { Subject } from 'rxjs/internal/Subject';
import { OrderDto } from '../../../core/model/order/order.dto';
import { OrderStatusDto } from '../../../core/model/order/order-status.dto';
import { ActivatedRoute, Router } from '@angular/router';
import { BillingStateService } from '../../../core/services/billing-state.service';

@Component({
  selector: 'app-order-handle',
  templateUrl: './order-handle.component.html',
  styleUrls: ['./order-handle.component.scss']
})
export class OrderHandleComponent implements OnInit, OnDestroy {

  $ordersCreatedSubject: Subject<OrderDto[]> = new Subject<OrderDto[]>();
  $ordersDeliveredSubject: Subject<OrderDto[]> = new Subject<OrderDto[]>();

  constructor(private orderService: OrderService,
              private socketService: WebSocketService,
              private billingStateService: BillingStateService,
              private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.getOrders();
    this.socketService.messages.subscribe(msg => {
      if (msg.content === 'UPDATE') {
        this.getOrders();
      }
    });
  }

  ngOnDestroy(): void {
    this.$ordersCreatedSubject.unsubscribe();
    this.$ordersDeliveredSubject.unsubscribe();
    this.socketService.messages.unsubscribe();
  }

  private getOrders() {
    this.orderService.getOrders().subscribe(response => {
      const ordersCreated = response.filter(order => order.orderStatus === OrderStatusDto.CREATED);
      const ordersDelivered = response.filter(order => order.orderStatus === OrderStatusDto.DELIVERED);

      this.$ordersCreatedSubject.next(ordersCreated);
      this.$ordersDeliveredSubject.next(ordersDelivered);
    });
  }


  navigateToBilling(order: OrderDto) {
    if (order.id !== undefined) {
      this.billingStateService.setOrderForBilling(order);
      this.router.navigate(['billing', order.id], {relativeTo: this.route});
    }

  }
}
