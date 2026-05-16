import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatChipsModule } from '@angular/material/chips';
import { CustomerService, Customer } from '../../core/services/customer.service';

@Component({
  selector: 'app-customer-list',
  standalone: true,
  imports: [CommonModule, MatTableModule, MatCardModule, MatButtonModule, MatIconModule, MatChipsModule],
  template: `
    <div class="page-container">
      <div class="header-actions">
        <h1 class="mat-headline-4">Customers</h1>
        <button mat-raised-button color="primary">
          <mat-icon>add</mat-icon> Add Customer
        </button>
      </div>

      <mat-card>
        <table mat-table [dataSource]="customers()" class="mat-elevation-z8">
          <ng-container matColumnDef="id">
            <th mat-header-cell *matHeaderCellDef> ID </th>
            <td mat-cell *matCellDef="let element"> {{element.id}} </td>
          </ng-container>

          <ng-container matColumnDef="name">
            <th mat-header-cell *matHeaderCellDef> Name </th>
            <td mat-cell *matCellDef="let element"> {{element.firstName}} {{element.lastName}} </td>
          </ng-container>

          <ng-container matColumnDef="email">
            <th mat-header-cell *matHeaderCellDef> Email </th>
            <td mat-cell *matCellDef="let element"> {{element.email}} </td>
          </ng-container>

          <ng-container matColumnDef="kyc">
            <th mat-header-cell *matHeaderCellDef> KYC Status </th>
            <td mat-cell *matCellDef="let element">
              <mat-chip [color]="element.kycStatus === 'VERIFIED' ? 'primary' : 'warn'">
                {{element.kycStatus}}
              </mat-chip>
            </td>
          </ng-container>

          <ng-container matColumnDef="actions">
            <th mat-header-cell *matHeaderCellDef> Actions </th>
            <td mat-cell *matCellDef="let element">
              <button mat-icon-button color="primary" (click)="validateKyc(element.id)" *ngIf="element.kycStatus !== 'VERIFIED'">
                <mat-icon>verified_user</mat-icon>
              </button>
            </td>
          </ng-container>

          <tr mat-header-row *matHeaderRowDef="displayedColumns"></tr>
          <tr mat-row *matRowDef="let row; columns: displayedColumns;"></tr>
        </table>
      </mat-card>
    </div>
  `,
  styles: [`
    .page-container { padding: 20px; }
    .header-actions {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 20px;
    }
    table { width: 100%; }
  `]
})
export class CustomerListComponent implements OnInit {
  private customerService = inject(CustomerService);
  
  customers = signal<Customer[]>([]);
  displayedColumns: string[] = ['id', 'name', 'email', 'kyc', 'actions'];

  ngOnInit(): void {
    this.loadCustomers();
  }

  loadCustomers(): void {
    this.customerService.getCustomers().subscribe(page => {
      this.customers.set(page.content);
    });
  }

  validateKyc(id: number): void {
    this.customerService.validateKyc(id).subscribe(() => {
      this.loadCustomers(); // Reload after validating
    });
  }
}
