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
    console.log('here');
    console.log(product);
    return this.httpClient.post<ProductDto>('http://localhost:8180/product/create', product);
  }
}
