import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { FlightSearchResponse } from '../models/flight.model';

@Injectable({ providedIn: 'root' })
export class FlightService {

  private bookingUrl = 'http://localhost:8084/api/bookings';

  constructor(private http: HttpClient) {}

  searchFlights(source: string, destination: string, travelDate: string): Observable<FlightSearchResponse[]> {
    const params = new HttpParams()
      .set('source', source)
      .set('destination', destination)
      .set('travelDate', travelDate);
    return this.http.get<FlightSearchResponse[]>(`${this.bookingUrl}/search`, { params });
  }
}