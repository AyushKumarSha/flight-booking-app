import { ComponentFixture, TestBed } from '@angular/core/testing';
import { PaymentComponent } from './payment';
import { provideHttpClient } from '@angular/common/http';
import { provideRouter } from '@angular/router';
import { routes } from '../../app.routes';

describe('PaymentComponent', () => {
  let component: PaymentComponent;
  let fixture: ComponentFixture<PaymentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PaymentComponent],
      providers: [provideHttpClient(), provideRouter(routes)]
    }).compileComponents();
    fixture = TestBed.createComponent(PaymentComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});