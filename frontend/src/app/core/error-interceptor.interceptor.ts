import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor, HttpErrorResponse
} from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { ToastrService } from 'ngx-toastr';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {

  constructor(private toastService: ToastrService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    return next.handle(request)
      .pipe(
        catchError((error: HttpErrorResponse) => {
          if (error.status === 404) {
            this.toastService.error('error: interceptor',
              'error: interceptor: ' + error.message);
          }
          this.toastService.error('error: interceptor: ' + error.message + error.error['errorMessage'],
            'error: interceptor code: ' + error.url);
          return throwError(() => new Error(error.message));
        })
      );
  }
}
