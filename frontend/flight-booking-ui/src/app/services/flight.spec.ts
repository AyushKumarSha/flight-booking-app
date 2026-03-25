import { TestBed } from '@angular/core/testing';
import { FlightService } from './flight.service';
import { provideHttpClient } from '@angular/common/http';

describe('FlightService', () => {
  let service: FlightService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient()]
    });
    service = TestBed.inject(FlightService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});