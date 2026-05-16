import { Component, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatGridListModule } from '@angular/material/grid-list';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatIconModule, MatGridListModule],
  changeDetection: ChangeDetectionStrategy.OnPush,
  template: `
    <div class="dashboard-container">
      <h1 class="mat-headline-4">Overview Dashboard</h1>
      
      <div class="stats-grid">
        <mat-card class="stat-card">
          <mat-card-header>
            <div mat-card-avatar class="stat-icon bg-primary">
              <mat-icon>people</mat-icon>
            </div>
            <mat-card-title>Total Customers</mat-card-title>
            <mat-card-subtitle>Active accounts</mat-card-subtitle>
          </mat-card-header>
          <mat-card-content>
            <h2>1,245</h2>
          </mat-card-content>
        </mat-card>

        <mat-card class="stat-card">
          <mat-card-header>
            <div mat-card-avatar class="stat-icon bg-success">
              <mat-icon>account_balance</mat-icon>
            </div>
            <mat-card-title>Total Accounts</mat-card-title>
            <mat-card-subtitle>Operating accounts</mat-card-subtitle>
          </mat-card-header>
          <mat-card-content>
            <h2>3,892</h2>
          </mat-card-content>
        </mat-card>

        <mat-card class="stat-card">
          <mat-card-header>
            <div mat-card-avatar class="stat-icon bg-warning">
              <mat-icon>security</mat-icon>
            </div>
            <mat-card-title>Fraud Alerts</mat-card-title>
            <mat-card-subtitle>Pending resolution</mat-card-subtitle>
          </mat-card-header>
          <mat-card-content>
            <h2>14</h2>
          </mat-card-content>
        </mat-card>
      </div>
    </div>
  `,
  styles: [`
    .dashboard-container {
      padding: 16px;
    }
    .stats-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
      gap: 20px;
      margin-top: 20px;
    }
    .stat-card {
      padding: 10px;
      
      h2 {
        font-size: 2.5rem;
        margin: 16px 0 0 16px;
        font-weight: 500;
      }
    }
    .stat-icon {
      display: flex;
      align-items: center;
      justify-content: center;
      color: white;
      border-radius: 50%;
    }
    .bg-primary { background-color: #1A237E; }
    .bg-success { background-color: #2E7D32; }
    .bg-warning { background-color: #F57F17; }
  `]
})
export class DashboardComponent {
}
