import { OrderItemDto } from './order-item.dto';

export interface OrderDto {
  orderAgent: string;
  deliverTo: string;
  orderItems: OrderItemDto[];
  commentFood?: string;
  commentDrink?: string;
  orderStatus?: string;
  id?: string;
}
