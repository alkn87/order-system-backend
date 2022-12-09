import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor, HttpErrorResponse
} from '@angular/common/http';
import { catchError, EMPTY, Observable } from 'rxjs';
import { ToastrService } from 'ngx-toastr';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {

  constructor(private toastService: ToastrService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    console.log("Passed through the interceptor in request");
    return next.handle(request)
      .pipe(
        catchError((error: HttpErrorResponse) => {
          this.toastService.error('Message: ' + error.message,
            'Error: ' + error.status);
          return EMPTY;
        })
      );
  }
}
