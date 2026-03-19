import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { FlightService } from '../../services/flight.service';
import { FlightSearchResponse } from '../../models/flight.model';

@Component({
  selector: 'app-search-results',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './search-results.html'
})
export class SearchResultsComponent implements OnInit {
  flights: FlightSearchResponse[] = [];
  filteredFlights: FlightSearchResponse[] = [];
  loading = false;
  errorMessage = '';
  source = '';
  destination = '';
  travelDate = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private flightService: FlightService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.source = params['source'] || '';
      this.destination = params['destination'] || '';
      this.travelDate = params['travelDate'] || '';
      if (this.source && this.destination && this.travelDate) {
        this.searchFlights();
      } else {
        this.loading = false;
        this.errorMessage = 'Missing search parameters';
        this.cdr.detectChanges();
      }
    });
  }

  searchFlights(): void {
    this.loading = true;
    this.errorMessage = '';
    this.flights = [];
    this.filteredFlights = [];
    this.cdr.detectChanges();

    this.flightService.searchFlights(this.source, this.destination, this.travelDate).subscribe({
      next: (data) => {
        this.flights = data;
        this.filteredFlights = [...data];
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Search failed:', err);
        this.errorMessage = 'Failed to fetch flights. Please check if all services are running.';
        this.flights = [];
        this.filteredFlights = [];
        this.loading = false;
        this.cdr.detectChanges();
      }
    });
  }

  onBook(flight: FlightSearchResponse): void {
    this.router.navigate(['/booking'], {
      queryParams: {
        routeId: flight.routeId,
        flightNumber: flight.flightNumber,
        source: flight.source,
        destination: flight.destination,
        travelDate: this.travelDate,
        amount: flight.dynamicPrice
      }
    });
  }

  filterByPrice(max: number): void {
    this.filteredFlights = this.flights.filter(f => f.dynamicPrice <= max);
    this.cdr.detectChanges();
  }

  clearFilter(): void {
    this.filteredFlights = [...this.flights];
    this.cdr.detectChanges();
  }
}