import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';
import { OrderMainComponent } from './order-main/order-main.component';
import { OrderRoutingModule } from './order-routing/order-routing.module';



@NgModule({
  declarations: [
    OrderMainComponent
  ],
  imports: [
    CommonModule,
    OrderRoutingModule,
    SharedModule
  ]
})
export class OrderModule { }
