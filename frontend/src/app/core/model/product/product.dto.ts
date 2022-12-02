import { ProductTypeDto } from './product-type.dto';

export interface ProductDto {
  productName: string;
  productPrice: number;
  productType: ProductTypeDto;
}
