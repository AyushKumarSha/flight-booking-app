import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { FlightService } from '../../services/flight.service';
import { FlightSearchResponse } from '../../models/flight.model';
import { Location } from '@angular/common';


@Component({
  selector: 'app-search-results',
  standalone: true,
  imports: [CommonModule, FormsModule],
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

  maxPrice = 10000;
  selectedMaxPrice = 10000;

  airlines: string[] = [];
  selectedAirlines: string[] = [];

  airlineLogos: { [key: string]: string } = {
    'Air India': 'assets/airlines/air-india.png',
    'IndiGo': 'assets/airlines/indigo.png',
    'SpiceJet': 'assets/airlines/spicejet.png',
    'Vistara': 'assets/airlines/vistara.png',
  };

  airlineColors: { [key: string]: string } = {
    'Air India': '#ED1C24',
    'IndiGo': '#0052A5',
    'SpiceJet': '#FF4E00',
    'Vistara': '#582C83',
  };

  constructor(
  private route: ActivatedRoute,
  private router: Router,
  private flightService: FlightService,
  private cdr: ChangeDetectorRef,
  private location: Location
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

        if (data.length > 0) {
          const prices = data.map(f => f.dynamicPrice);
          this.maxPrice = Math.ceil(Math.max(...prices) / 1000) * 1000 + 1000;
          this.selectedMaxPrice = this.maxPrice;
        }

        this.airlines = [...new Set(data.map(f => f.airline))];
        this.selectedAirlines = [...this.airlines];

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

  applyFilters(): void {
    this.filteredFlights = this.flights.filter(f =>
      f.dynamicPrice <= this.selectedMaxPrice &&
      this.selectedAirlines.includes(f.airline)
    );
    this.cdr.detectChanges();
  }

  onPriceChange(): void {
    this.applyFilters();
  }

  toggleAirline(airline: string): void {
    if (this.selectedAirlines.includes(airline)) {
      this.selectedAirlines = this.selectedAirlines.filter(a => a !== airline);
    } else {
      this.selectedAirlines.push(airline);
    }
    this.applyFilters();
  }

  isAirlineSelected(airline: string): boolean {
    return this.selectedAirlines.includes(airline);
  }

  clearFilters(): void {
    this.selectedMaxPrice = this.maxPrice;
    this.selectedAirlines = [...this.airlines];
    this.filteredFlights = [...this.flights];
    this.cdr.detectChanges();
  }

  getAirlineLogo(airline: string): string {
    return this.airlineLogos[airline] || '';
  }

  getAirlineColor(airline: string): string {
    return this.airlineColors[airline] || '#0052A5';
  }

  getAirlineInitial(airline: string): string {
    return airline.charAt(0).toUpperCase();
  }

  getFlightDuration(dep: string, arr: string): string {
    const [dh, dm] = dep.split(':').map(Number);
    const [ah, am] = arr.split(':').map(Number);
    let mins = (ah * 60 + am) - (dh * 60 + dm);
    if (mins < 0) mins += 1440;
    const h = Math.floor(mins / 60);
    const m = mins % 60;
    return `${h}h ${m}m`;
  }
  goBack(): void {
    this.router.navigate(['/home']);
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
}