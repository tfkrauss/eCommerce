import {Component, inject} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {Product, ProductWithInventoryRequest} from "../../model/product";
import {ProductService} from "../../services/product/product.service";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-add-product',
  standalone: true,
  imports: [ReactiveFormsModule, NgIf],
  templateUrl: './add-product.component.html',
  styleUrl: './add-product.component.css'
})
export class AddProductComponent {
  addProductForm: FormGroup;
  private readonly productService = inject(ProductService);
  productCreated = false;

  constructor(private fb: FormBuilder) {
    this.addProductForm = this.fb.group({
      skuCode: ['', [Validators.required]],
      name: ['', [Validators.required]],
      description: ['', [Validators.required]],
      price: [0, [Validators.required]],
      quantity: [0, [Validators.required]]
    })
  }

  //Add call to inventory service here?
  onSubmit(): void {
    if (this.addProductForm.valid) {
      // Build the product object from form inputs
      const product: Product = {
        skuCode: this.addProductForm.get('skuCode')?.value,
        name: this.addProductForm.get('name')?.value,
        description: this.addProductForm.get('description')?.value,
        price: this.addProductForm.get('price')?.value,
      }
      // Build ProductWithInventoryRequest to include product and quantity
      const productWithInventory: ProductWithInventoryRequest = {
        product: product,
        quantity: this.addProductForm.get('quantity')?.value
      };
      console.log(productWithInventory);
      this.productService.createProduct(productWithInventory).subscribe(response => {
        console.log('Product and inventory created successfully:', response);
        this.productCreated = true;
        this.addProductForm.reset();
      })
    } else {
      console.log('Error creating product or updating inventory', Error);
    }
  }

  get skuCode() {
    return this.addProductForm.get('skuCode');
  }

  get name() {
    return this.addProductForm.get('name');
  }

  get description() {
    return this.addProductForm.get('description');
  }

  get price() {
    return this.addProductForm.get('price');
  }

  get quantity() {
    return this.addProductForm.get('quantity');
  }
}
