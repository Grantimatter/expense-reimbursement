import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AppService {

  authenticated = false;

  constructor(private httpClient: HttpClient) {

  }

  authenticate(credentials, callback) {
    const headers = new HttpHeaders(credentials ? {
      authroization: 'Basic ' + btoa(credentials.username + ':' +
        credentials.password)
    } : {});

    this.httpClient.get('user', { headers: headers }).subscribe(response => {
      if (response['name']) {
        this.authenticated = true;
      } else {
        this.authenticated = false;
      }

      return callback && callback();
    });
  }
}
