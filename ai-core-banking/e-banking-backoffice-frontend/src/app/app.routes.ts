import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./shared/components/admin-layout/admin-layout.component').then(m => m.AdminLayoutComponent),
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      { 
        path: 'dashboard', 
        loadComponent: () => import('./features/dashboard/dashboard.component').then(m => m.DashboardComponent),
        data: { title: 'Dashboard' }
      },
      { 
        path: 'customers', 
        loadComponent: () => import('./features/customers/customer-list.component').then(m => m.CustomerListComponent),
        data: { title: 'Customers' }
      },
      { 
        path: 'accounts', 
        loadComponent: () => import('./features/accounts/account-list.component').then(m => m.AccountListComponent),
        data: { title: 'Accounts' }
      },
      { 
        path: 'fraud', 
        loadComponent: () => import('./features/fraud/fraud-list.component').then(m => m.FraudListComponent),
        data: { title: 'Fraud Alerts' }
      }
    ]
  },
  { path: '**', redirectTo: '' }
];
