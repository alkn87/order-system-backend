import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductMainComponent } from './product-main.component';
import { CreateProductComponent } from '../create-product/create-product.component';
import { SharedModule } from '../../shared/shared.module';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('ProductMainComponent', () => {
  let component: ProductMainComponent;
  let fixture: ComponentFixture<ProductMainComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProductMainComponent, CreateProductComponent ],
      imports: [ SharedModule, NoopAnimationsModule, HttpClientTestingModule ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductMainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
