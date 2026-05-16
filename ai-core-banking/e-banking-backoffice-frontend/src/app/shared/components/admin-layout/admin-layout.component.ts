import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-admin-layout',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatSidenavModule,
    MatToolbarModule,
    MatListModule,
    MatIconModule,
    MatButtonModule
  ],
  template: `
    <mat-sidenav-container class="sidenav-container">
      <mat-sidenav #sidenav mode="side" opened class="sidenav">
        <div class="logo-container">
          <h2>AI Bank</h2>
        </div>
        <mat-nav-list>
          <a mat-list-item routerLink="/dashboard" routerLinkActive="active">
            <mat-icon matListItemIcon>dashboard</mat-icon>
            <div matListItemTitle>Dashboard</div>
          </a>
          <a mat-list-item routerLink="/customers" routerLinkActive="active">
            <mat-icon matListItemIcon>people</mat-icon>
            <div matListItemTitle>Customers</div>
          </a>
          <a mat-list-item routerLink="/accounts" routerLinkActive="active">
            <mat-icon matListItemIcon>account_balance</mat-icon>
            <div matListItemTitle>Accounts</div>
          </a>
          <a mat-list-item routerLink="/cards" routerLinkActive="active">
            <mat-icon matListItemIcon>credit_card</mat-icon>
            <div matListItemTitle>Cards</div>
          </a>
          <a mat-list-item routerLink="/transactions" routerLinkActive="active">
            <mat-icon matListItemIcon>receipt_long</mat-icon>
            <div matListItemTitle>Transactions</div>
          </a>
          <a mat-list-item routerLink="/fraud" routerLinkActive="active">
            <mat-icon matListItemIcon>security</mat-icon>
            <div matListItemTitle>Fraud Alerts</div>
          </a>
        </mat-nav-list>
      </mat-sidenav>
      <mat-sidenav-content>
        <mat-toolbar color="primary">
          <button mat-icon-button (click)="sidenav.toggle()">
            <mat-icon>menu</mat-icon>
          </button>
          <span>AI Core Banking Backoffice</span>
          <span class="spacer"></span>
          <button mat-icon-button>
            <mat-icon>notifications</mat-icon>
          </button>
          <button mat-icon-button>
            <mat-icon>account_circle</mat-icon>
          </button>
        </mat-toolbar>
        <div class="main-content">
          <router-outlet></router-outlet>
        </div>
      </mat-sidenav-content>
    </mat-sidenav-container>
  `,
  styles: [`
    .sidenav-container {
      height: 100vh;
    }
    .sidenav {
      width: 250px;
    }
    .logo-container {
      padding: 20px;
      text-align: center;
      background-color: #1A237E;
      color: white;
      h2 { margin: 0; }
    }
    .spacer {
      flex: 1 1 auto;
    }
    .main-content {
      padding: 20px;
    }
    .active {
      background-color: rgba(0, 0, 0, 0.04);
      border-right: 4px solid #1A237E;
    }
  `]
})
export class AdminLayoutComponent implements OnInit {
  constructor() {}
  ngOnInit(): void {}
}
