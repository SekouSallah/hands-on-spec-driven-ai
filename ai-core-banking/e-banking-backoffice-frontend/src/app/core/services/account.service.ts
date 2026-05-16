import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { Observable } from 'rxjs';
import { Page } from './customer.service';

export interface Account {
  id: number;
  accountNumber: string;
  balance: number;
  status: string;
  customerId: number;
}

@Injectable({ providedIn: 'root' })
export class AccountService extends ApiService {
  private readonly PATH = '/accounts';

  getAccounts(page: number = 0, size: number = 20): Observable<Page<Account>> {
    const params = new URLSearchParams({ page: page.toString(), size: size.toString() });
    return this.get<Page<Account>>(`${this.PATH}?${params.toString()}`);
  }

  createAccount(data: any): Observable<Account> {
    return this.post<Account>(this.PATH, data);
  }

  freezeAccount(id: number): Observable<Account> {
    return this.patch<Account>(`${this.PATH}/${id}/freeze`, {});
  }
}
