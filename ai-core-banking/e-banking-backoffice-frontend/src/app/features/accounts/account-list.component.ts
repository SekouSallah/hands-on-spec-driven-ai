import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatChipsModule } from '@angular/material/chips';
import { AccountService, Account } from '../../core/services/account.service';

@Component({
  selector: 'app-account-list',
  standalone: true,
  imports: [CommonModule, MatTableModule, MatCardModule, MatButtonModule, MatIconModule, MatChipsModule],
  template: `
    <div class="page-container">
      <div class="header-actions">
        <h1 class="mat-headline-4">Accounts</h1>
      </div>

      <mat-card>
        <table mat-table [dataSource]="accounts()" class="mat-elevation-z8">
          <ng-container matColumnDef="id">
            <th mat-header-cell *matHeaderCellDef> ID </th>
            <td mat-cell *matCellDef="let element"> {{element.id}} </td>
          </ng-container>

          <ng-container matColumnDef="accountNumber">
            <th mat-header-cell *matHeaderCellDef> Account Number </th>
            <td mat-cell *matCellDef="let element"> {{element.accountNumber}} </td>
          </ng-container>

          <ng-container matColumnDef="balance">
            <th mat-header-cell *matHeaderCellDef> Balance </th>
            <td mat-cell *matCellDef="let element"> {{element.balance | currency}} </td>
          </ng-container>

          <ng-container matColumnDef="status">
            <th mat-header-cell *matHeaderCellDef> Status </th>
            <td mat-cell *matCellDef="let element">
              <mat-chip [color]="element.status === 'ACTIVE' ? 'primary' : 'warn'">
                {{element.status}}
              </mat-chip>
            </td>
          </ng-container>

          <ng-container matColumnDef="actions">
            <th mat-header-cell *matHeaderCellDef> Actions </th>
            <td mat-cell *matCellDef="let element">
              <button mat-icon-button color="warn" (click)="freezeAccount(element.id)" *ngIf="element.status === 'ACTIVE'">
                <mat-icon>ac_unit</mat-icon>
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
    .header-actions { margin-bottom: 20px; }
    table { width: 100%; }
  `]
})
export class AccountListComponent implements OnInit {
  private accountService = inject(AccountService);
  
  accounts = signal<Account[]>([]);
  displayedColumns: string[] = ['id', 'accountNumber', 'balance', 'status', 'actions'];

  ngOnInit(): void {
    this.loadAccounts();
  }

  loadAccounts(): void {
    this.accountService.getAccounts().subscribe(page => {
      this.accounts.set(page.content);
    });
  }

  freezeAccount(id: number): void {
    this.accountService.freezeAccount(id).subscribe(() => {
      this.loadAccounts();
    });
  }
}
