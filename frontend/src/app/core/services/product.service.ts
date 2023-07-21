import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ProductDto } from '../model/product/product.dto';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { SalesEntryDto } from '../model/sales/sales-entry.dto';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private httpClient: HttpClient) {
  }

  createProduct(product: ProductDto): Observable<any> {
    return this.httpClient.post<ProductDto>(environment.apiBaseUrl + '/product/create', product);
  }

  updateProduct(product: ProductDto): Observable<any> {
    return this.httpClient.put<ProductDto>(environment.apiBaseUrl + '/product/update', product);
  }

  deleteProduct(product: ProductDto): Observable<any> {
    return this.httpClient.request<ProductDto>('delete', environment.apiBaseUrl + '/product/delete', { body: product });
  }

  getAllProducts(): Observable<ProductDto[]> {
    return this.httpClient.get<ProductDto[]>(environment.apiBaseUrl + '/product');
  }

  getTotalSales(): Observable<SalesEntryDto> {
    return this.httpClient.get<SalesEntryDto>(environment.ordersBaseUrl + '/orders/sales');
  }

  getTotalRevenue(): Observable<number> {
    return this.httpClient.get<number>(environment.ordersBaseUrl + '/orders/total');
  }

  blockProduct(product: ProductDto): Observable<ProductDto> {
    return this.httpClient.post<ProductDto>(environment.apiBaseUrl + '/product/block', product);
  }
}
