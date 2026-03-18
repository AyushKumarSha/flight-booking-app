import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BookingRequest, BookingResponse } from '../models/booking.model';

@Injectable({ providedIn: 'root' })
export class BookingService {

  private bookingUrl = 'http://localhost:8084/api/bookings';

  constructor(private http: HttpClient) {}

  initiateBooking(request: BookingRequest): Observable<BookingResponse> {
    return this.http.post<BookingResponse>(this.bookingUrl, request);
  }

  getBookingById(bookingId: number): Observable<BookingResponse> {
    return this.http.get<BookingResponse>(`${this.bookingUrl}/${bookingId}`);
  }

  getBookingsByUser(userId: number): Observable<BookingResponse[]> {
    return this.http.get<BookingResponse[]>(`${this.bookingUrl}/user/${userId}`);
  }
}