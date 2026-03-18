import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { FlightService } from '../../services/flight.service';
import { FlightSearchResponse } from '../../models/flight.model';
import { AuthService } from '../../services/auth.service';

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
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.source = params['source'];
      this.destination = params['destination'];
      this.travelDate = params['travelDate'];
      this.searchFlights();
    });
  }

  searchFlights(): void {
    this.loading = true;
    this.flightService.searchFlights(this.source, this.destination, this.travelDate).subscribe({
      next: (data) => {
        this.flights = data;
        this.filteredFlights = data;
        this.loading = false;
      },
      error: () => {
        this.errorMessage = 'Failed to fetch flights. Please try again.';
        this.loading = false;
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
  }

  clearFilter(): void {
    this.filteredFlights = this.flights;
  }
}