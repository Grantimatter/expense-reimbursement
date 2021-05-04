import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ApiService } from '../reimbursement-service/api/api.service';

@Component({
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss']
})
export class LoginPageComponent implements OnInit {

  loginForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private apiService:ApiService, private router:Router) {
    this.loginForm = this.formBuilder.group({
      usernameOrEmail: [null, [Validators.required]],
      password: [null, Validators.required]
    });
   }

  ngOnInit(): void {
    
  }

  submit(): void {
    if(!this.loginForm.valid) return;
    this.apiService.login(this.loginForm.value, ()=> this.router.navigate(["/home"]));
  }
}
