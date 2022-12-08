import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';
import { StationRoutingModule } from './station-routing/station-routing.module';
import { StationMainComponent } from './station-main/station-main.component';



@NgModule({
  declarations: [
    StationMainComponent
  ],
  imports: [
    CommonModule,
    StationRoutingModule,
    SharedModule
  ]
})
export class StationModule { }
