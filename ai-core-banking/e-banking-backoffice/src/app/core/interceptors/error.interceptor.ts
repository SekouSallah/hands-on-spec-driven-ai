import { HttpInterceptorFn } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  return next(req).pipe(
    catchError((error) => {
      // In a real app, inject MatSnackBar or a global error service here to show standard errors
      console.error('API Error:', error);
      return throwError(() => error);
    })
  );
};
