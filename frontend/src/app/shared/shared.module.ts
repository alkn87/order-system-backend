import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MainToolbarComponent } from './main-toolbar/main-toolbar.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';



@NgModule({
  declarations: [
    MainToolbarComponent
  ],
  exports: [
    MainToolbarComponent
  ],
  imports: [
    CommonModule,
    MatButtonModule,
    MatToolbarModule
  ]
})
export class SharedModule { }
