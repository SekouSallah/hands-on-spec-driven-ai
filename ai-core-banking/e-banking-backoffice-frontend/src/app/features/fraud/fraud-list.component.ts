import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatChipsModule } from '@angular/material/chips';
import { FraudService, FraudAlert } from '../../core/services/fraud.service';

@Component({
  selector: 'app-fraud-list',
  standalone: true,
  imports: [CommonModule, MatTableModule, MatCardModule, MatButtonModule, MatIconModule, MatChipsModule],
  template: `
    <div class="page-container">
      <div class="header-actions">
        <h1 class="mat-headline-4">Fraud Alerts</h1>
      </div>

      <mat-card>
        <table mat-table [dataSource]="alerts()" class="mat-elevation-z8">
          <ng-container matColumnDef="id">
            <th mat-header-cell *matHeaderCellDef> ID </th>
            <td mat-cell *matCellDef="let element"> {{element.id}} </td>
          </ng-container>

          <ng-container matColumnDef="level">
            <th mat-header-cell *matHeaderCellDef> Level </th>
            <td mat-cell *matCellDef="let element">
              <mat-chip [color]="element.level === 'HIGH' ? 'warn' : 'accent'">
                {{element.level}}
              </mat-chip>
            </td>
          </ng-container>

          <ng-container matColumnDef="reason">
            <th mat-header-cell *matHeaderCellDef> Reason </th>
            <td mat-cell *matCellDef="let element"> {{element.reason}} </td>
          </ng-container>

          <ng-container matColumnDef="transactionId">
            <th mat-header-cell *matHeaderCellDef> TX ID </th>
            <td mat-cell *matCellDef="let element"> {{element.transactionId}} </td>
          </ng-container>

          <ng-container matColumnDef="actions">
            <th mat-header-cell *matHeaderCellDef> Actions </th>
            <td mat-cell *matCellDef="let element">
              <button mat-icon-button color="primary" (click)="resolveAlert(element.id)" matTooltip="Resolve Alert">
                <mat-icon>check_circle</mat-icon>
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
export class FraudListComponent implements OnInit {
  private fraudService = inject(FraudService);
  
  alerts = signal<FraudAlert[]>([]);
  displayedColumns: string[] = ['id', 'level', 'reason', 'transactionId', 'actions'];

  ngOnInit(): void {
    this.loadAlerts();
  }

  loadAlerts(): void {
    this.fraudService.getUnresolvedAlerts().subscribe(page => {
      this.alerts.set(page.content);
    });
  }

  resolveAlert(id: number): void {
    this.fraudService.resolveAlert(id).subscribe(() => {
      this.loadAlerts();
    });
  }
}
