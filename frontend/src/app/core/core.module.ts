import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { ToastrModule } from 'ngx-toastr';
import { globalToastConfig } from './toast/toast-config';
import { ErrorInterceptor } from './error-interceptor.interceptor';
import { KeycloakAngularModule } from 'keycloak-angular';
import { AuthGuard } from './auth.guard';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';



@NgModule({
  declarations: [],
  imports: [
    BrowserAnimationsModule,
    CommonModule,
    HttpClientModule,
    KeycloakAngularModule,
    ToastrModule.forRoot(globalToastConfig)
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
    AuthGuard
  ]
})
export class CoreModule { }
