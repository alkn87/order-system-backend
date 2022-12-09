import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ProductDto } from '../model/product/product.dto';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private httpClient: HttpClient) { }

  createProduct(product: ProductDto): Observable<any> {
    return this.httpClient.post<ProductDto>('http://localhost:8180/product/create', product);
  }

  getAllProducts(): Observable<ProductDto[]> {
    return this.httpClient.get<ProductDto[]>('http://localhost:8180/product');
  }

  blockProduct(product: ProductDto): Observable<ProductDto> {
    return this.httpClient.post<ProductDto>('http://localhost:8180/product/block', product);
  }
}
