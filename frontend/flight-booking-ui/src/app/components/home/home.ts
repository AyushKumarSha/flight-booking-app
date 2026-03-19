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

  cities = ['Mumbai', 'Delhi', 'Bangalore', 'Chennai', 'Kolkata',
            'Hyderabad', 'Pune', 'Ahmedabad', 'Jaipur', 'Goa'];

  filteredSources: string[] = [];
  filteredDestinations: string[] = [];
  showSourceDropdown = false;
  showDestinationDropdown = false;

  constructor(private router: Router) {}

  onSourceInput(): void {
    const val = this.source.toLowerCase();
    this.filteredSources = this.cities.filter(c => c.toLowerCase().startsWith(val));
    this.showSourceDropdown = true;
  }

  onDestinationInput(): void {
    const val = this.destination.toLowerCase();
    this.filteredDestinations = this.cities.filter(c => c.toLowerCase().startsWith(val));
    this.showDestinationDropdown = true;
  }

  selectSource(city: string): void {
    this.source = city;
    this.showSourceDropdown = false;
  }

  selectDestination(city: string): void {
    this.destination = city;
    this.showDestinationDropdown = false;
  }

  hideDropdowns(): void {
    setTimeout(() => {
      this.showSourceDropdown = false;
      this.showDestinationDropdown = false;
    }, 200);
  }

  onSearch(): void {
    if (!this.source || !this.destination || !this.travelDate) {
      this.errorMessage = 'Please fill in all search fields';
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