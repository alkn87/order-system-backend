import { TestBed } from '@angular/core/testing';

import { AuthGuard } from './auth.guard';
import { KeycloakService } from 'keycloak-angular';

describe('AuthGuard', () => {
  let guard: AuthGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        KeycloakService
      ],
    });
    guard = TestBed.inject(AuthGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
