import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { ApiService } from '../reimbursement-service/api/api.service';

@Component({
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss']
})
export class LoginPageComponent implements OnInit {

  loginForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private apiService: ApiService, private router: Router, private http: HttpClient) {
    this.loginForm = this.formBuilder.group({
      usernameOrEmail: [null, [Validators.required]],
      password: [null, Validators.required]
    });
  }

  title = 'Demo';
  greeting = {};

  ngOnInit(): void {
    
    this.http.get(`${environment.apiUrl}/user`).subscribe(
      data => {
        this.greeting = data;
        console.log("data: ", data);
      },
      err => {
        console.log(err);
      }
    );
    
  }

  submit(): void {
    if (!this.loginForm.valid) return;
    this.apiService.login(this.loginForm.value, () => this.router.navigate(["/home"]));
  }
}
