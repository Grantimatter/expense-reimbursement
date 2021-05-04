import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewReimbursementDialogComponent } from './new-reimbursement-dialog.component';

describe('NewReimbursementDialogComponent', () => {
  let component: NewReimbursementDialogComponent;
  let fixture: ComponentFixture<NewReimbursementDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewReimbursementDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewReimbursementDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
