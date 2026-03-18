import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { BookingService } from '../../services/booking.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-payment',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './payment.html'
})
export class PaymentComponent implements OnInit {
  routeId = 0;
  flightNumber = '';
  source = '';
  destination = '';
  travelDate = '';
  amount = 0;

  paymentMode = 'CARD';
  cardNumber = '';
  cardExpiry = '';
  cardCvv = '';
  errorMessage = '';
  loading = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private bookingService: BookingService,
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

  validateCard(): boolean {
    if (this.paymentMode === 'CARD') {
      if (this.cardNumber.replace(/\s/g, '').length !== 16) {
        this.errorMessage = 'Card number must be 16 digits';
        return false;
      }
      if (!this.cardExpiry.match(/^\d{2}\/\d{2}$/)) {
        this.errorMessage = 'Expiry must be in MM/YY format';
        return false;
      }
      if (this.cardCvv.length < 3) {
        this.errorMessage = 'Invalid CVV';
        return false;
      }
    }
    return true;
  }

  onPay(): void {
    this.errorMessage = '';
    if (!this.validateCard()) return;

    this.loading = true;
    const userId = this.authService.getUserId()!;

    this.bookingService.initiateBooking({
      userId,
      routeId: this.routeId,
      travelDate: this.travelDate,
      paymentMode: this.paymentMode,
      cardNumber: this.cardNumber,
      cardExpiry: this.cardExpiry,
      cardCvv: this.cardCvv
    }).subscribe({
      next: (booking) => {
        // Poll for result after 4 seconds (wait for Kafka)
        setTimeout(() => {
          this.bookingService.getBookingById(booking.bookingId).subscribe({
            next: (result) => {
              if (result.status === 'SUCCESSFUL') {
                this.router.navigate(['/payment-success'], {
                  queryParams: { bookingId: booking.bookingId, amount: this.amount }
                });
              } else {
                this.router.navigate(['/payment-failed']);
              }
            }
          });
        }, 4000);
      },
      error: (err) => {
        this.errorMessage = err.error?.error || 'Payment initiation failed';
        this.loading = false;
      }
    });
  }

  goBack(): void {
    this.router.navigate(['/booking']);
  }
}