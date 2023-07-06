import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ProductDto } from '../model/product/product.dto';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private httpClient: HttpClient) { }

  createProduct(product: ProductDto): Observable<any> {
    return this.httpClient.post<ProductDto>(environment.apiBaseUrl + '/product/create', product);
  }

  getAllProducts(): Observable<ProductDto[]> {
    return this.httpClient.get<ProductDto[]>(environment.apiBaseUrl + '/product');
  }

  blockProduct(product: ProductDto): Observable<ProductDto> {
    return this.httpClient.post<ProductDto>(environment.apiBaseUrl + '/product/block', product);
  }
}
