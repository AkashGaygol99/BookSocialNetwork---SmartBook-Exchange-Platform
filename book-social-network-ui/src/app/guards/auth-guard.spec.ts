import { describe, it, expect, beforeEach, vi } from 'vitest';
import { TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { AuthGuard } from '../guards/auth-guard';
import { AuthService } from '../service/auth-service';

describe('AuthGuard (Vitest)', () => {
  let guard: AuthGuard;
  let authServiceMock: any;
  let routerMock: any;

  beforeEach(() => {
    authServiceMock = {
      isLoggedIn: vi.fn()
    };

    routerMock = {
      navigate: vi.fn()
    };

    TestBed.configureTestingModule({
      providers: [
        AuthGuard,
        { provide: AuthService, useValue: authServiceMock },
        { provide: Router, useValue: routerMock }
      ]
    });

    guard = TestBed.inject(AuthGuard);
  });

  it('should allow access when user is logged in', () => {
    authServiceMock.isLoggedIn.mockReturnValue(true);
    expect(guard.canActivate()).toBe(true);
  });

  it('should block access and redirect when not logged in', () => {
    authServiceMock.isLoggedIn.mockReturnValue(false);

    expect(guard.canActivate()).toBe(false);
    expect(routerMock.navigate).toHaveBeenCalledWith(['/login']);
  });
});