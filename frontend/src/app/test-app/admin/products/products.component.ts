import { Component, OnInit, ViewChild } from '@angular/core';
import { ServicesService } from '../../services.service';
import { MatPaginator } from '@angular/material/paginator';
import { Product } from '../../models/product';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {
  products: any;
  dataSource: any;

  displayedColumns: string[] = ['productId', 'productName', 'brand', 'prodPrice', 'productCode' ,'actions'];
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private service: ServicesService) { }

  ngOnInit(): void {
    this.showProducts();
  }

  showProducts(): void {
    this.service.showProducts().subscribe({
      next: (data) => {
        this.products = data;
        this.dataSource=new MatTableDataSource<Product>(this.products)
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      },
      error: (err) => {
        console.log(err);
      }
    });
  }

  deleteProduct(productId: number): void {
    if (confirm('Are you sure you want to delete this product?')) {
      this.service.deleteProduct(productId).subscribe({
        next: () => {
          // Product successfully deleted, remove it from the products list
          this.products = this.products.filter((product: any) => product.productId !== productId);
        },
        error: (err) => {
          console.log('Error deleting product:', err);
        }
      });
    }
  }
  FilterChange(event:Event){
    const filvalue=(event.target as HTMLInputElement).value;
    this.dataSource.filter=filvalue;
  }
}
