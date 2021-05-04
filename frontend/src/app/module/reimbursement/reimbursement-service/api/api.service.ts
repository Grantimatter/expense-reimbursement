import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { env } from 'process';
import { BehaviorSubject } from 'rxjs';
import { User } from 'src/app/model/User';
import { environment } from 'src/environments/environment';
import { ReimbursementServiceModule } from '../reimbursement-service.module';

@Injectable({
  providedIn: ReimbursementServiceModule
})
export class ApiService {

  constructor(private httpClient: HttpClient) { }

  public user: BehaviorSubject<User> ;

  public login(loginObject?: any, onSuccess?: (response) => void, onFail?: (error) => void) {

    if (loginObject) {
      this.httpClient.post(`${environment.apiUrl}/user/login`, loginObject).subscribe(
        (response: User) => {
          this.user = new BehaviorSubject<User>(response);
          //this.user.next(response);
          if (onSuccess) onSuccess(response);
          console.log("User logged in successfully!", this.user.value);
        },
        (error) => {
          if (onFail) onFail(error);
        }
      );
    } else {
      this.httpClient.get(`${environment.apiUrl}/user/check`).subscribe(
        (response: User) => {
          console.log("Checking if user is logged in!", response);
          this.user.next(response);
          if (onSuccess) onSuccess(response);
        },
        (error) => onFail && onFail(error)
      );
    }

  }

  public logout(onSuccess?: (response) => void, onFail?: (error) => void): void {
    this.httpClient.get(`${environment.apiUrl}/user/logout`).subscribe(
      (response) => {
        this.user.next(null);
        if (onSuccess) onSuccess(response)
      },
      (error) => onFail && onFail(error)
    );
  }

  public updateUser(onSuccess?: (response) => void, onFail?: (error) => void): void {
    this.httpClient.get(`${environment.apiUrl}/user?username=${this.user.value.username}`).subscribe(
      (response: User) => {
        this.user.next(response);
        if (onSuccess) onSuccess(response);
      },
      (error) => onFail && onFail(error)
    );
  }

}
