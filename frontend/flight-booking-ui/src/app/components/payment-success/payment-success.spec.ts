import { ComponentFixture, TestBed } from '@angular/core/testing';
import { PaymentSuccessComponent } from './payment-success';
import { provideRouter } from '@angular/router';
import { routes } from '../../app.routes';

describe('PaymentSuccessComponent', () => {
  let component: PaymentSuccessComponent;
  let fixture: ComponentFixture<PaymentSuccessComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PaymentSuccessComponent],
      providers: [provideRouter(routes)]
    }).compileComponents();
    fixture = TestBed.createComponent(PaymentSuccessComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});