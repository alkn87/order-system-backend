import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrderHandleComponent } from './order-handle.component';
import { SharedModule } from '../../../shared/shared.module';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ToastrModule } from 'ngx-toastr';
import { globalToastConfig } from '../../../core/toast/toast-config';
import { ActivatedRoute } from '@angular/router';

describe('OrderHandleComponent', () => {
  let component: OrderHandleComponent;
  let fixture: ComponentFixture<OrderHandleComponent>;

  const fakeActivatedRoute = {
    snapshot: { data: {  } }
  } as ActivatedRoute;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OrderHandleComponent ],
      imports: [ SharedModule, NoopAnimationsModule, HttpClientTestingModule, ToastrModule.forRoot(globalToastConfig) ],
      providers: [{provide: ActivatedRoute, useValue: fakeActivatedRoute}]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OrderHandleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
