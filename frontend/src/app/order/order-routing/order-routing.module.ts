import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OrderMainComponent } from '../order-main/order-main.component';
import { OrderBillingComponent } from '../order-handle/order-billing/order-billing.component';


const routes: Routes = [
  {
    path: '',
    component: OrderMainComponent
  },
  {
    path: 'billing/:id',
    component: OrderBillingComponent
  }
];

@NgModule({
  declarations: [],
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [RouterModule]
})
export class OrderRoutingModule { }
