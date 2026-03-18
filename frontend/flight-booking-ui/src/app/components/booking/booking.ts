import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-booking',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './booking.html'
})
export class BookingComponent implements OnInit {
  routeId = 0;
  flightNumber = '';
  source = '';
  destination = '';
  travelDate = '';
  amount = 0;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.routeId = +params['routeId'];
      this.flightNumber = params['flightNumber'];
      this.source = params['source'];
      this.destination = params['destination'];
      this.travelDate = params['travelDate'];
      this.amount = +params['amount'];
    });
  }

  proceedToPayment(): void {
    this.router.navigate(['/payment'], {
      queryParams: {
        routeId: this.routeId,
        flightNumber: this.flightNumber,
        source: this.source,
        destination: this.destination,
        travelDate: this.travelDate,
        amount: this.amount
      }
    });
  }

  goBack(): void {
    this.router.navigate(['/search-results']);
  }
}