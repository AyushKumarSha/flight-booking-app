import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { PaymentService } from '../../services/payment.service';
import { PaymentResponse } from '../../models/booking.model';

@Component({
  selector: 'app-payment-success',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './payment-success.html'
})
export class PaymentSuccessComponent implements OnInit {
  bookingId = 0;
  amount = 0;
  payment: PaymentResponse | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private paymentService: PaymentService
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.bookingId = +params['bookingId'];
      this.amount = +params['amount'];
      this.paymentService.getPaymentByBookingId(this.bookingId).subscribe({
        next: (data) => this.payment = data
      });
    });
  }

  goHome(): void {
    this.router.navigate(['/home']);
  }

  downloadTicket(): void {
    const content = `
      FLIGHT BOOKING CONFIRMATION
      ===========================
      Booking ID   : ${this.bookingId}
      Transaction  : ${this.payment?.transactionId}
      Amount Paid  : ₹${this.amount}
      Status       : CONFIRMED
      Date         : ${new Date().toLocaleDateString()}
    `;
    const blob = new Blob([content], { type: 'text/plain' });
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = `ticket-${this.bookingId}.txt`;
    a.click();
  }
}