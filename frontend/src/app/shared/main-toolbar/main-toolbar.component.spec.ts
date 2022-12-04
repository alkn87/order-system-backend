import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MainToolbarComponent } from './main-toolbar.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { NO_ERRORS_SCHEMA } from '@angular/core';

describe('MainToolbarComponent', () => {
  let component: MainToolbarComponent;
  let fixture: ComponentFixture<MainToolbarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MainToolbarComponent ],
      imports: [MatToolbarModule],
      schemas: [ NO_ERRORS_SCHEMA ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MainToolbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
