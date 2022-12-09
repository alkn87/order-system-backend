import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrderMainComponent } from './order-main.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { SharedModule } from '../../shared/shared.module';
import { KeycloakService } from 'keycloak-angular';
import { ToastrModule } from 'ngx-toastr';
import { globalToastConfig } from '../../core/toast/toast-config';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { NO_ERRORS_SCHEMA } from '@angular/core';

describe('OrderMainComponent', () => {
  let component: OrderMainComponent;
  let fixture: ComponentFixture<OrderMainComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OrderMainComponent ],
      providers: [ KeycloakService ],
      imports: [ SharedModule, NoopAnimationsModule, HttpClientTestingModule, ToastrModule.forRoot(globalToastConfig) ],
      schemas: [ NO_ERRORS_SCHEMA ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OrderMainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
