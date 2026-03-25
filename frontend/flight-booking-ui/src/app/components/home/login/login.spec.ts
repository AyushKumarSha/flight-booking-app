import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LoginComponent } from './login';
import { provideHttpClient } from '@angular/common/http';
import { provideRouter } from '@angular/router';
import { routes } from '../../../app.routes';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LoginComponent, FormsModule, CommonModule],
      providers: [
        provideHttpClient(),
        provideRouter(routes)
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should show error when fields empty', () => {
    component.email = '';
    component.password = '';
    component.onLogin();
    expect(component.errorMessage).toBe('Please fill in all fields');
  });

  it('should have loading false initially', () => {
    expect(component.loading).toBeFalsy();
  });
});