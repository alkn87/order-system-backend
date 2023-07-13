import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { OrderDto } from '../model/order/order.dto';
import { Observable } from 'rxjs';
import { StationOrderDto } from '../model/station/station-order.dto';
import { environment } from '../../../environments/environment';
import { OrderBillingDto } from '../model/order/oder-billing.dto';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private httpClient: HttpClient) { }

  createOrder(order: OrderDto): Observable<any> {
    return this.httpClient.post<OrderDto>(environment.apiBaseUrl + '/order/create', order);
  }

  getOrders(): Observable<OrderDto[]> {
    return this.httpClient.get<OrderDto[]>(environment.apiBaseUrl + '/order');
  }

  getOrdersForBilling(): Observable<OrderBillingDto[]> {
    return this.httpClient.get<OrderBillingDto[]>(environment.apiBaseUrl + '/order/billing');
  }

  billOrder(deliverTo: string): Observable<any> {
    return this.httpClient.post(environment.apiBaseUrl + '/order/bill', deliverTo, {responseType: 'text'});
  }

  getOrdersByStation(stationType: string): Observable<StationOrderDto[]> {
    return this.httpClient.get<StationOrderDto[]>(environment.apiBaseUrl + '/order/station/' + stationType);
  }

  finishStationOrder(stationOrderId: string): Observable<any> {
    return this.httpClient.post(environment.apiBaseUrl + '/order/station/finish', stationOrderId, {responseType: 'text'});
  }
}
