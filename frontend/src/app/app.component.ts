import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AppService } from './app.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  title = 'reimbursements2';

  constructor(private app: AppService, private httpClient: HttpClient, private router: Router) {
    this.app.authenticate(undefined, undefined);
  }
/*
  logout() {
    this.httpClient.post('logout', {}).finally(() => {
      this.app.authenticated = false;
      this.router.navigateByUrl('/login');
    }).subscribe();
  }
  */
}
