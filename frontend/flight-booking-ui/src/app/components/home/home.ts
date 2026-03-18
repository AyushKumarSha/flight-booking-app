import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

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

  constructor(private router: Router, private authService: AuthService) {}

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