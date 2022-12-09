import { Injectable } from '@angular/core';
import { OrderDto } from '../model/order/order.dto';

@Injectable({
  providedIn: 'root'
})
export class BillingStateService {

  orderForBilling: OrderDto = {deliverTo: '', orderAgent: '', orderItems: []};

  setOrderForBilling(order: OrderDto) {
    this.orderForBilling = order;
  }
}
