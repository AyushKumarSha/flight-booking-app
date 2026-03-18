import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-payment-failed',
  standalone: true,
  imports: [],
  templateUrl: './payment-failed.html'
})
export class PaymentFailedComponent {
  constructor(private router: Router) {}

  tryAgain(): void {
    this.router.navigate(['/home']);
  }
}