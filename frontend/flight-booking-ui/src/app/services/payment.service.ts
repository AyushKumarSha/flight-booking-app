import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PaymentResponse } from '../models/booking.model';

@Injectable({ providedIn: 'root' })
export class PaymentService {

  private paymentUrl = 'http://localhost:8085/api/payments';

  constructor(private http: HttpClient) {}

  getPaymentByBookingId(bookingId: number): Observable<PaymentResponse> {
    return this.http.get<PaymentResponse>(`${this.paymentUrl}/booking/${bookingId}`);
  }
}