import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { OrderDto } from '../model/order/order.dto';
import { Observable } from 'rxjs';
import { StationOrderDto } from '../model/station/station-order.dto';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private httpClient: HttpClient) { }

  createOrder(order: OrderDto): Observable<any> {
    return this.httpClient.post<OrderDto>('http://localhost:8180/order/create', order);
  }

  getOrdersByStation(stationType: string): Observable<StationOrderDto[]> {
    return this.httpClient.get<StationOrderDto[]>('http://localhost:8180/order/station/' + stationType);
  }

  finishStationOrder(stationOrderId: string): Observable<any> {
    return this.httpClient.post('http://localhost:8180/order/station/finish', stationOrderId, {responseType: 'text'});
  }
}
