import { TestBed } from '@angular/core/testing';
import { PaymentService } from './payment.service';
import { provideHttpClient } from '@angular/common/http';

describe('PaymentService', () => {
  let service: PaymentService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient()]
    });
    service = TestBed.inject(PaymentService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});