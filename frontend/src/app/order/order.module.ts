import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';
import { OrderMainComponent } from './order-main/order-main.component';
import { OrderRoutingModule } from './order-routing/order-routing.module';
import { OrderHandleComponent } from './order-handle/order-handle/order-handle.component';
import { OrderBillingComponent } from './order-handle/order-billing/order-billing.component';



@NgModule({
  declarations: [
    OrderMainComponent,
    OrderHandleComponent,
    OrderBillingComponent
  ],
  imports: [
    CommonModule,
    OrderRoutingModule,
    SharedModule
  ]
})
export class OrderModule { }
