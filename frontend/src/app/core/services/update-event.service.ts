import { Injectable, NgZone } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UpdateEventService {

  private eventSource: EventSource | undefined;
  private url = environment.apiBaseUrl + '/updates';

  constructor(private _zone: NgZone) {
  }

  getServerSentEvent(): Observable<any> {
    return new Observable(observer => {
      if (!this.eventSource) {
        this.eventSource = new EventSource(this.url);
      }

      this.eventSource.onmessage = event => {
        this._zone.run(() => {
          observer.next(event);
        });
      };

      this.eventSource.onerror = error => {
        this._zone.run(() => {
          observer.error(error);
        });
      };
    });
  }

  closeEventSource() {
    this.eventSource?.close();
    this.eventSource = undefined;
  }
}
