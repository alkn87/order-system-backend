import { Component, OnDestroy, OnInit } from '@angular/core';
import { OrderService } from '../../core/services/order.service';
import { Observable, of, Subscription } from 'rxjs';
import { StationOrderDto } from '../../core/model/station/station-order.dto';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { UpdateEventService } from '../../core/services/update-event.service';

@Component({
  selector: 'app-station-main',
  templateUrl: './station-main.component.html',
  styleUrls: ['./station-main.component.scss']
})
export class StationMainComponent implements OnInit, OnDestroy {

  stationForm: FormGroup = new FormGroup({});

  $stationTypes: Observable<string[]> = of(['DRINK', 'FOOD']);
  $stationOrders: Observable<StationOrderDto[]> = of([]);

  constructor(private formBuilder: FormBuilder,
              private orderService: OrderService,
              private updateEventService: UpdateEventService,
              private toastService: ToastrService) {
  }

  ngOnDestroy(): void {
    this.updateEventService.closeEventSource();
  }


  ngOnInit(): void {
    this.stationForm = this.formBuilder.group({
      stationType: this.formBuilder.control('', Validators.required)
    });

    this.stationForm.controls['stationType'].valueChanges.subscribe(value => {
      this.$stationOrders = this.orderService.getOrdersByStation(value);
    });

    this.updateEventService.getServerSentEvent()
      .subscribe({
          next: (data: MessageEvent) => {
            console.log('SSE received');
            console.log( JSON.parse(data.data)['data']);
            if (JSON.parse(data.data)['data'] === 'UPDATE') {
              if (this.stationForm.valid) {
                this.$stationOrders = this.orderService.getOrdersByStation(this.stationForm.controls['stationType'].value);
              }
            }
          },
          error: () => console.error(`Error: ${this}`)
        }
      );
  }

  finishStationOrder(stationOrderId: string) {
    this.orderService.finishStationOrder(stationOrderId).subscribe(_ => {
      this.toastService.success('Station order completion initiated. Order ID: ' + stationOrderId);
    });
  }


}
