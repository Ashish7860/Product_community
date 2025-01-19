import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ServicesService } from 'src/app/test-app/services.service';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent implements OnInit {

  errorMessage: string = '';

  addPostForm: any;

  constructor(private service: ServicesService, private router: Router) { }

  ngOnInit(): void {
  }

  addProduct(form: any) {
    let newPost = {
      productName: form.value.productName,
      brand: form.value.brand,
      prodPrice: form.value.prodPrice,
      productCode: form.value.productCode,
      id: this.service.getUser().id
    }

    this.service.addProduct(newPost).subscribe({
      next: (data) => {
        this.router.navigate(['admin/products']);
      },
      error: (err) => {
        console.log(err);
      }
    });
  } 
}
