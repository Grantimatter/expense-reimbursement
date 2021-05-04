import { Pipe, PipeTransform } from '@angular/core';
import { User } from '../model/User';

@Pipe({
  name: 'fullName'
})
export class FullNamePipe implements PipeTransform {

  transform(user: User, ...args: unknown[]): string {
    return user.firstName + " " + user.lastName;
  }

}
