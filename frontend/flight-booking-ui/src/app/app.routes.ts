import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home';
import { SearchResultsComponent } from './components/search-results/search-results';
import { BookingComponent } from './components/booking/booking';
import { PaymentComponent } from './components/payment/payment';
import { PaymentSuccessComponent } from './components/payment-success/payment-success';
import { PaymentFailedComponent } from './components/payment-failed/payment-failed';
import { RegisterComponent } from './components/register/register';
import { LoginComponent } from './components/login/login';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'home', component: HomeComponent },
  { path: 'search-results', component: SearchResultsComponent },
  { path: 'booking', component: BookingComponent },
  { path: 'payment', component: PaymentComponent },
  { path: 'payment-success', component: PaymentSuccessComponent },
  { path: 'payment-failed', component: PaymentFailedComponent },
  { path: '**', redirectTo: 'login' }
];