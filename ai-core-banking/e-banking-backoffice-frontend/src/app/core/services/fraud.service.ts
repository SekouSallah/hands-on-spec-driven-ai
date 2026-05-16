import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { Observable } from 'rxjs';
import { Page } from './customer.service';

export interface FraudAlert {
  id: number;
  level: string;
  score: number;
  reason: string;
  transactionId: number;
  resolved: boolean;
  createdAt: string;
}

@Injectable({ providedIn: 'root' })
export class FraudService extends ApiService {
  private readonly PATH = '/fraud';

  getAlerts(page: number = 0, size: number = 20): Observable<Page<FraudAlert>> {
    const params = new URLSearchParams({ page: page.toString(), size: size.toString() });
    return this.get<Page<FraudAlert>>(`${this.PATH}?${params.toString()}`);
  }

  getUnresolvedAlerts(page: number = 0, size: number = 20): Observable<Page<FraudAlert>> {
    const params = new URLSearchParams({ resolved: 'false', page: page.toString(), size: size.toString() });
    return this.get<Page<FraudAlert>>(`${this.PATH}/status?${params.toString()}`);
  }

  resolveAlert(id: number): Observable<FraudAlert> {
    return this.patch<FraudAlert>(`${this.PATH}/${id}/resolve`, {});
  }
}
