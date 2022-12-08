import { StationOrderItemsDto } from './station-order-items.dto';

export interface StationOrderDto {
  stationOrderItems: StationOrderItemsDto[];
  stationType: string;
  status: string;
  deliverTo: string;
  id: string;
}
