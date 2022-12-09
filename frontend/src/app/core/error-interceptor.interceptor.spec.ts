import { TestBed } from '@angular/core/testing';

import { ErrorInterceptor } from './error-interceptor.interceptor';
import { ToastrModule } from 'ngx-toastr';
import { globalToastConfig } from './toast/toast-config';

describe('ErrorInterceptor', () => {
  beforeEach(() => TestBed.configureTestingModule({
    providers: [
      ErrorInterceptor
      ],
    imports: [ToastrModule.forRoot(globalToastConfig)]
  }));

  it('should be created', () => {
    const interceptor: ErrorInterceptor = TestBed.inject(ErrorInterceptor);
    expect(interceptor).toBeTruthy();
  });
});
