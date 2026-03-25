import { ComponentFixture, TestBed } from '@angular/core/testing';
import { PaymentFailedComponent } from './payment-failed';
import { provideRouter } from '@angular/router';
import { routes } from '../../app.routes';

describe('PaymentFailedComponent', () => {
  let component: PaymentFailedComponent;
  let fixture: ComponentFixture<PaymentFailedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PaymentFailedComponent],
      providers: [provideRouter(routes)]
    }).compileComponents();
    fixture = TestBed.createComponent(PaymentFailedComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});