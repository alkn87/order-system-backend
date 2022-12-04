import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProductService } from '../../core/services/product.service';
import { ProductDto } from '../../core/model/product/product.dto';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-create-product',
  templateUrl: './create-product.component.html',
  styleUrls: ['./create-product.component.scss']
})
export class CreateProductComponent implements OnInit {

  createProductForm: FormGroup = new FormGroup({});

  constructor(private formBuilder: FormBuilder, private productService: ProductService, private toastService: ToastrService) {
  }

  ngOnInit(): void {
    this.createProductForm = this.formBuilder.group(
      {
        productName: this.formBuilder.control('', Validators.required),
        productPrice: this.formBuilder.control('', [Validators.required, Validators.pattern(/^(\d+\.\d{1,2}$)|^(\d*)$/)]),
        productType: this.formBuilder.control('', Validators.required)
      }
    );
  }

  createProduct(): void {
    console.log('happened');
    let product: ProductDto = {
      productName: this.createProductForm.controls['productName'].value,
      productPrice: Number(this.createProductForm.controls['productPrice'].value).valueOf(),
      productType: this.createProductForm.controls['productType'].value,
    }
    this.productService.createProduct(product).subscribe(response => {
      if( response.hasOwnProperty("code") && response.code.indexOf("ERROR") > -1 ){
        this._onError(response);
      } else {
        this._onSuccess(response);
        this.createProductForm.reset();
      }
    });
  }

  private _onError(error: Error): void{
    this.toastService.error(error.message);
  }

  private _onSuccess(product: ProductDto): void{
    this.toastService.success("Product with name '" + product.productName + "' was created.");
  }

}
