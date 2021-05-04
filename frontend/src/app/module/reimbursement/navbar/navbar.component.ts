import { Component, OnInit, ChangeDetectionStrategy } from '@angular/core';
import { Router } from '@angular/router';
import { ApiService } from '../reimbursement-service/api/api.service';
import { FullNamePipe } from 'src/app/pipe/full-name.pipe';
import { BehaviorSubject } from 'rxjs';
import { User } from 'src/app/model/User';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class NavbarComponent implements OnInit {

  user: BehaviorSubject<User>;

  constructor(private router: Router, private apiService: ApiService) {
    this.user = apiService.user;
  }

  ngOnInit(): void {
    // this.apiService.user.subscribe(
    //   (newUser:User) => {
    //     this.user = newUser;
    //     if(newUser != null) {
    //     console.log("New user!", this.user);
    //     }
    //   }
    // );
  }

  links: any[] = [
    { title: "Home", uri: "/home" }
  ];

  logout(): void {
    this.apiService.logout();
    this.router.navigate(["/login"]);
    console.log("Logging out!");
  }

}
