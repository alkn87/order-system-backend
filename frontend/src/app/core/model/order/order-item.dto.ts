import { ProductTypeDto } from '../product/product-type.dto';

export interface OrderItemDto {
  productName: string;
  productType: ProductTypeDto;
  productPrice: number;
  quantity: number;
}
