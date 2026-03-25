import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HomeComponent } from './home';
import { provideRouter } from '@angular/router';
import { routes } from '../../app.routes';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

describe('HomeComponent', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HomeComponent, FormsModule, CommonModule],
      providers: [provideRouter(routes)]
    }).compileComponents();

    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should show error when search fields empty', () => {
    component.source = '';
    component.destination = '';
    component.travelDate = '';
    component.onSearch();
    expect(component.errorMessage).toBe('Please fill in all search fields');
  });

  it('should swap airports correctly', () => {
    component.source = 'Mumbai';
    component.destination = 'Delhi';
    component.swapAirports();
    expect(component.source).toBe('Delhi');
    expect(component.destination).toBe('Mumbai');
  });

  it('should filter cities on source input', () => {
    component.source = 'Mu';
    component.onSourceInput();
    expect(component.filteredSources.length).toBeGreaterThan(0);
    expect(component.filteredSources[0].name).toBe('Mumbai');
  });
});