import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './home.html'
})
export class HomeComponent {
  source = '';
  destination = '';
  travelDate = '';
  errorMessage = '';

  airports = [
    { code: 'BOM', name: 'Mumbai', display: 'Mumbai (BOM)' },
    { code: 'DEL', name: 'Delhi', display: 'Delhi (DEL)' },
    { code: 'BLR', name: 'Bangalore', display: 'Bangalore (BLR)' },
    { code: 'MAA', name: 'Chennai', display: 'Chennai (MAA)' },
    { code: 'CCU', name: 'Kolkata', display: 'Kolkata (CCU)' },
    { code: 'HYD', name: 'Hyderabad', display: 'Hyderabad (HYD)' },
    { code: 'PNQ', name: 'Pune', display: 'Pune (PNQ)' },
    { code: 'AMD', name: 'Ahmedabad', display: 'Ahmedabad (AMD)' },
    { code: 'JAI', name: 'Jaipur', display: 'Jaipur (JAI)' },
    { code: 'GOI', name: 'Goa', display: 'Goa (GOI)' },
  ];

  filteredSources: any[] = [];
  filteredDestinations: any[] = [];
  showSourceDropdown = false;
  showDestinationDropdown = false;

  constructor(private router: Router) {}

  onSourceInput(): void {
    const val = this.source.toLowerCase();
    this.filteredSources = this.airports.filter(a =>
      a.name.toLowerCase().startsWith(val) ||
      a.code.toLowerCase().startsWith(val)
    );
    this.showSourceDropdown = true;
  }

  onDestinationInput(): void {
    const val = this.destination.toLowerCase();
    this.filteredDestinations = this.airports.filter(a =>
      a.name.toLowerCase().startsWith(val) ||
      a.code.toLowerCase().startsWith(val)
    );
    this.showDestinationDropdown = true;
  }

  selectSource(airport: any): void {
    this.source = airport.name;
    this.showSourceDropdown = false;
  }

  selectDestination(airport: any): void {
    this.destination = airport.name;
    this.showDestinationDropdown = false;
  }

  hideDropdowns(): void {
    setTimeout(() => {
      this.showSourceDropdown = false;
      this.showDestinationDropdown = false;
    }, 200);
  }

  swapAirports(): void {
    const temp = this.source;
    this.source = this.destination;
    this.destination = temp;
  }

  onSearch(): void {
    if (!this.source || !this.destination || !this.travelDate) {
      this.errorMessage = 'Please fill in all search fields';
      return;
    }
    if (this.source === this.destination) {
      this.errorMessage = 'Source and destination cannot be the same';
      return;
    }
    this.router.navigate(['/search-results'], {
      queryParams: {
        source: this.source,
        destination: this.destination,
        travelDate: this.travelDate
      }
    });
  }
}