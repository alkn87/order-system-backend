import { Injectable } from '@angular/core';
import { AnonymousSubject, Subject } from 'rxjs/internal/Subject';
import { map, Observable, Observer } from 'rxjs';

export interface WebsocketMessage {
  source: string;
  content: string;
}

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {

  WEBSOCKET_SERVER_URL = 'ws://localhost:8180/ws'

  private subject: AnonymousSubject<MessageEvent> | undefined;
  public messages: Subject<WebsocketMessage>;


  constructor() {
    this.messages = <Subject<WebsocketMessage>>this.connect(this.WEBSOCKET_SERVER_URL).pipe(
      map(
        (response: MessageEvent): WebsocketMessage => {
          console.log(response.data);
          return JSON.parse(response.data);
        }
      )
    );
  }

  public connect(url: string): AnonymousSubject<MessageEvent> {
    if (!this.subject) {
      this.subject = this.create(url);
      console.log('Successfully connected: ' + url);
    }
    return this.subject;
  }

  private create(url: string): AnonymousSubject<MessageEvent> {
    let ws = new WebSocket(url);
    let observable = new Observable((obs: Observer<MessageEvent>) => {
      ws.onmessage = obs.next.bind(obs);
      ws.onerror = obs.error.bind(obs);
      ws.onclose = obs.complete.bind(obs);
      return ws.close.bind(ws);
    });
    let observer = {
      error: (err: any) => console.error(err),
      complete: () => console.log('completed'),
      next: (data: Object) => {
        console.log('Message sent to websocket: ', data);
        if (ws.readyState === WebSocket.OPEN) {
          ws.send(JSON.stringify(data));
        }
      }
    };
    return new AnonymousSubject<MessageEvent>(observer, observable);
  }
}
