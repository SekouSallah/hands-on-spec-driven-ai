import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { Observable } from 'rxjs';

export interface Customer {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  phone: string;
  status: string;
  kycStatus: string;
}

export interface Page<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}

@Injectable({ providedIn: 'root' })
export class CustomerService extends ApiService {
  private readonly PATH = '/customers';

  getCustomers(page: number = 0, size: number = 20): Observable<Page<Customer>> {
    const params = new URLSearchParams({ page: page.toString(), size: size.toString() });
    return this.get<Page<Customer>>(`${this.PATH}?${params.toString()}`);
  }

  createCustomer(data: any): Observable<Customer> {
    return this.post<Customer>(this.PATH, data);
  }

  validateKyc(id: number): Observable<Customer> {
    return this.patch<Customer>(`${this.PATH}/${id}/kyc`, {});
  }
}
