import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginPageComponent } from './login-page/login-page.component';
import { HomePageComponent } from './home-page/home-page.component';
import { DemoMaterialModule } from 'src/app/material-module';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { ReimbursementServiceModule } from './reimbursement-service/reimbursement-service.module';
import { NavbarComponent } from './navbar/navbar.component';
import { RouterModule } from '@angular/router';
import { NewReimbursementDialogComponent } from './new-reimbursement-dialog/new-reimbursement-dialog.component';

@NgModule({
  declarations: [
    LoginPageComponent,
    HomePageComponent,
    NavbarComponent,
    NewReimbursementDialogComponent
  ],
  imports: [
    CommonModule,
    DemoMaterialModule,
    ReactiveFormsModule,
    ReimbursementServiceModule,
    RouterModule
  ],
  exports: [
    LoginPageComponent,
    HomePageComponent,
    NavbarComponent
  ]
})
export class ReimbursementModule { }
