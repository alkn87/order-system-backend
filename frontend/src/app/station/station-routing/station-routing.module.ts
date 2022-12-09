import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { StationMainComponent } from '../station-main/station-main.component';



const routes: Routes = [
  {
    path: '',
    component: StationMainComponent
  }
];

@NgModule({
  declarations: [],
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [RouterModule]
})
export class StationRoutingModule { }
