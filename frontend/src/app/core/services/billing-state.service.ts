import { Injectable } from '@angular/core';
import { OrderBillingDto } from '../model/order/oder-billing.dto';

@Injectable({
  providedIn: 'root'
})
export class BillingStateService {

  orderForBilling: OrderBillingDto = {deliverTo: '', orderItems: []};

  setOrderForBilling(order: OrderBillingDto) {
    this.orderForBilling = order;
  }
}
