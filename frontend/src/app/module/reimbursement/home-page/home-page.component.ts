import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { BehaviorSubject } from 'rxjs';
import { Reimburesement } from 'src/app/model/Reimbursement';
import { User } from 'src/app/model/User';
import { NewReimbursementDialogComponent } from '../new-reimbursement-dialog/new-reimbursement-dialog.component';
import { ApiService } from '../reimbursement-service/api/api.service';

@Component({
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss']
})
export class HomePageComponent implements AfterViewInit, OnInit {

  dataSource: MatTableDataSource<Reimburesement>;
  displayedColumns: string[] = ["type", "amount", "description", "submitted", "status"];
  statusFilter = "all";
  typeFilter = "all";
  user: BehaviorSubject<User>;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private apiService: ApiService, private router: Router, public reimbursementDialog:MatDialog) { }

  reimbursements: Reimburesement[] = [];

  ngOnInit(): void {
    if (this.apiService.user && this.apiService.user.value) {
      this.initializePage();
    } else {
      this.apiService.login(null, (response) => {
        console.log(this.apiService.user.value);
        this.initializePage();
      }, (error) => this.router.navigate(["/login"]));
    }
  }

  initializePage(): void {
    this.reimbursements = this.apiService.user.value.authoredReimbursementList;
    this.dataSource = new MatTableDataSource(this.reimbursements);
    this.user = this.apiService.user;
  }

  ngAfterViewInit() {
    if (this.dataSource) {
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    }
  }

  getTotalAmount(): number {
    if (this.reimbursements) {
      return this.reimbursements.map(t => t.amount).reduce((acc, value) => acc + value, 0);
    }
    return 0;
  }

  applyFilter($event: Event): void {
    const filterValue = ($event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  newReimbursement(): void {
    const dialogRef = this.reimbursementDialog.open(NewReimbursementDialogComponent, {
      width: "max-content"
    });

    dialogRef.afterClosed().subscribe(result=>{
      console.log("Dialog closed");
    });
  }
}
