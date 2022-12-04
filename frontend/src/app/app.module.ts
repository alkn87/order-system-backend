import { APP_INITIALIZER, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { SharedModule } from './shared/shared.module';
import { CoreModule } from './core/core.module';
import { KeycloakService } from 'keycloak-angular';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    SharedModule,
    CoreModule
  ],
  providers: [
    {
      provide: APP_INITIALIZER,
      useFactory: initializer,
      multi: true,
      deps: [KeycloakService]
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

export function initializer(keycloak: KeycloakService): () => Promise<any> {
  return (): Promise<any> => new Promise(async (resolve, reject) => {
    keycloak.init({
      config: {
        url: 'http://localhost:7475/',
        realm: 'Order-System',
        clientId: 'order-system',
      },
      initOptions: {
        onLoad: 'login-required',
        checkLoginIframe: false
      },
      loadUserProfileAtStartUp: false,
      enableBearerInterceptor: true
    }).then((ok) => {
      resolve(ok);
    })
      .catch(error => reject(error));
  });
}
