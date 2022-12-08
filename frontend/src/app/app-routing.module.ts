import { NgModule, Optional, SkipSelf } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthGuard } from './core/auth.guard';

const routes: Routes = [
  {
    path: 'product',
    canActivate: [AuthGuard],
    data: {roles: ['admin', 'manager']},
    loadChildren: () => import('./product/product.module').then(m => m.ProductModule)
  },
  {
    path: 'order',
    canActivate: [AuthGuard],
    data: {roles: ['admin', 'manager', 'service']},
    loadChildren: () => import('./order/order.module').then(m => m.OrderModule)
  },
  {
    path: 'station',
    canActivate: [AuthGuard],
    data: {roles: ['admin', 'manager', 'service']},
    loadChildren: () => import('./station/station.module').then(m => m.StationModule)
  }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, {preloadingStrategy: PreloadAllModules}),
    CommonModule
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {
  constructor(@Optional() @SkipSelf() parentModule: AppRoutingModule) {
    if (parentModule) {
      throw new Error('AppRoutingModule is already loaded. Import it in the AppModule only.');
    }
  }
}
