import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { env } from 'process';
import { BehaviorSubject } from 'rxjs';
import { AppService } from 'src/app/app.service';
import { User } from 'src/app/model/User';
import { environment } from 'src/environments/environment';
import { ReimbursementServiceModule } from '../reimbursement-service.module';

@Injectable({
  providedIn: ReimbursementServiceModule
})
export class ApiService {

  constructor(private httpClient: HttpClient, private app:AppService) { }

  public user: BehaviorSubject<User> ;

  headers = new HttpHeaders({"Access-Control-Allow-Origin":"*"});

  public login(loginObject?: any, onSuccess?: (response) => void, onFail?: (error) => void) {


    const login:Object = {"username":loginObject.usernameOrEmail, "password":loginObject.password};
    
    this.app.authenticate(login, ()=>{

      if (loginObject) {
        this.httpClient.post(`${environment.apiUrl}/user/login`, loginObject, {headers: this.headers}).subscribe(
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
        this.httpClient.get(`${environment.apiUrl}/user/check`, {headers: this.headers}).subscribe(
          (response: User) => {
            console.log("Checking if user is logged in!", response);
            this.user.next(response);
            if (onSuccess) onSuccess(response);
          },
          (error) => onFail && onFail(error)
        );
      }
    });

  }

  public logout(onSuccess?: (response) => void, onFail?: (error) => void): void {
    this.httpClient.get(`${environment.apiUrl}/user/logout`, {headers: this.headers}).subscribe(
      (response) => {
        this.user.next(null);
        if (onSuccess) onSuccess(response)
      },
      (error) => onFail && onFail(error)
    );
  }

  public updateUser(onSuccess?: (response) => void, onFail?: (error) => void): void {
    this.httpClient.get(`${environment.apiUrl}/user?username=${this.user.value.username}`, {headers: this.headers}).subscribe(
      (response: User) => {
        this.user.next(response);
        if (onSuccess) onSuccess(response);
      },
      (error) => onFail && onFail(error)
    );
  }

}
