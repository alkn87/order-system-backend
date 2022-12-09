import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StationMainComponent } from './station-main.component';
import { SharedModule } from '../../shared/shared.module';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ToastrModule } from 'ngx-toastr';
import { globalToastConfig } from '../../core/toast/toast-config';

describe('StationMainComponent', () => {
  let component: StationMainComponent;
  let fixture: ComponentFixture<StationMainComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StationMainComponent ],
      imports: [ SharedModule, NoopAnimationsModule, HttpClientTestingModule, ToastrModule.forRoot(globalToastConfig) ]

    })
    .compileComponents();

    fixture = TestBed.createComponent(StationMainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
