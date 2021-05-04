import { Injectable } from '@angular/core';
import { EntityCollectionServiceBase, EntityCollectionServiceElementsFactory } from '@ngrx/data';
import { User } from 'src/app/model/User';
import { ReimbursementServiceModule } from '../reimbursement-service.module';

@Injectable({
  providedIn: ReimbursementServiceModule
})
export class UserService extends EntityCollectionServiceBase<User> {

  constructor(serviceElementsFactory: EntityCollectionServiceElementsFactory) { 
    super('User', serviceElementsFactory);
  }
}
