import { OrderItemDto } from './order-item.dto';

export interface OrderBillingDto {
  deliverTo: string;
  orderItems: OrderItemDto[];
}
