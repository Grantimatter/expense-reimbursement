import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';
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

    this.httpClient.get(`${environment.apiUrl}/user`, { headers: headers }).subscribe(response => {
      if (response['first_name']) {
        this.authenticated = true;
      } else {
        this.authenticated = false;
      }

      return callback && callback();
    });
  }
}
