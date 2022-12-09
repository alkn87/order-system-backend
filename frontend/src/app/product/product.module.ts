import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';
import { CreateProductComponent } from './create-product/create-product.component';
import { ProductMainComponent } from './product-main/product-main.component';
import { ProductRoutingModule } from './product-routing/product-routing.module';



@NgModule({
  declarations: [
    CreateProductComponent,
    ProductMainComponent
  ],
  imports: [
    CommonModule,
    ProductRoutingModule,
    SharedModule
  ]
})
export class ProductModule { }
