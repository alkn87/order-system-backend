import { Component, OnInit } from '@angular/core';
import { OrderService } from '../../core/services/order.service';
import { Observable, of } from 'rxjs';
import { StationOrderDto } from '../../core/model/station/station-order.dto';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { WebSocketService } from '../../core/services/web-socket.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-station-main',
  templateUrl: './station-main.component.html',
  styleUrls: ['./station-main.component.scss']
})
export class StationMainComponent implements OnInit {

  stationForm: FormGroup = new FormGroup({});

  $stationTypes: Observable<string[]> = of(['DRINK', 'FOOD']);
  $stationOrders: Observable<StationOrderDto[]> = of([]);

  constructor(private formBuilder: FormBuilder,
              private orderService: OrderService,
              private socketService: WebSocketService,
              private toastService: ToastrService) {
  }

  ngOnInit(): void {
    this.stationForm = this.formBuilder.group({
      stationType: this.formBuilder.control('', Validators.required)
    });
    this.stationForm.controls['stationType'].valueChanges.subscribe(value => {
      this.$stationOrders = this.orderService.getOrdersByStation(value);
    });
    this.socketService.messages.subscribe(msg => {
      if (msg.content === 'UPDATE') {
        if (this.stationForm.valid) {
          this.$stationOrders = this.orderService.getOrdersByStation(this.stationForm.controls['stationType'].value);
        }
      }
    });
  }

  finishStationOrder(stationOrderId: string) {
    this.orderService.finishStationOrder(stationOrderId).subscribe(_ => {
      this.toastService.success('Station order completion initiated. Order ID: '  + stationOrderId);
    });
  }


}
