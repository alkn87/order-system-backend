import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { OrderDto } from '../model/order/order.dto';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private httpClient: HttpClient) { }

  createOrder(order: OrderDto): Observable<any> {
    return this.httpClient.post<OrderDto>('http://localhost:8180/order/create', order);
  }
}
