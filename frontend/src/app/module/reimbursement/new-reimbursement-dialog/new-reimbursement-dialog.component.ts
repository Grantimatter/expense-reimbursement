import { Component, OnInit, ChangeDetectionStrategy, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Reimburesement } from 'src/app/model/Reimbursement';

@Component({
  selector: 'app-new-reimbursement-dialog',
  templateUrl: './new-reimbursement-dialog.component.html',
  styleUrls: ['./new-reimbursement-dialog.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class NewReimbursementDialogComponent {

  typeInput:any;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any) { }

}
