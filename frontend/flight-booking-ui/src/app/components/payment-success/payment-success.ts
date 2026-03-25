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
%PDF-1.4
1 0 obj
<< /Type /Catalog /Pages 2 0 R >>
endobj
`;
  // Use browser print as PDF instead
  const printWindow = window.open('', '_blank');
  if (printWindow) {
    printWindow.document.write(`
      <html>
        <head>
          <title>Flight Ticket - Booking ${this.bookingId}</title>
          <style>
            body { font-family: Arial, sans-serif; padding: 40px; }
            .header { background: #0052A5; color: white; padding: 20px; text-align: center; border-radius: 8px; }
            .ticket { border: 2px dashed #ccc; padding: 30px; margin: 20px 0; border-radius: 8px; }
            .row { display: flex; justify-content: space-between; margin: 10px 0; padding: 8px 0; border-bottom: 1px solid #eee; }
            .label { color: #666; font-size: 14px; }
            .value { font-weight: bold; font-size: 14px; }
            .success { color: #28a745; font-size: 18px; font-weight: bold; text-align: center; margin: 20px 0; }
            .footer { text-align: center; color: #999; font-size: 12px; margin-top: 30px; }
          </style>
        </head>
        <body>
          <div class="header">
            <h1>✈ FlightBook</h1>
            <p>E-Ticket / Boarding Pass</p>
          </div>
          <div class="ticket">
            <div class="success">✅ BOOKING CONFIRMED</div>
            <div class="row">
              <span class="label">Booking ID</span>
              <span class="value">#${this.bookingId}</span>
            </div>
            <div class="row">
              <span class="label">Transaction ID</span>
              <span class="value">${this.payment?.transactionId || 'N/A'}</span>
            </div>
            <div class="row">
              <span class="label">Amount Paid</span>
              <span class="value">₹${this.amount}</span>
            </div>
            <div class="row">
              <span class="label">Payment Mode</span>
              <span class="value">${this.payment?.paymentMode || 'N/A'}</span>
            </div>
            <div class="row">
              <span class="label">Status</span>
              <span class="value" style="color:green;">CONFIRMED ✓</span>
            </div>
            <div class="row">
              <span class="label">Date</span>
              <span class="value">${new Date().toLocaleDateString('en-IN')}</span>
            </div>
          </div>
          <div class="footer">
            <p>Thank you for booking with FlightBook!</p>
            <p>This is a computer generated ticket and does not require a signature.</p>
          </div>
          <script>
            window.onload = function() { window.print(); }
          </script>
        </body>
      </html>
    `);
    printWindow.document.close();
  }
}
}