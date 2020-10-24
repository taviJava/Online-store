import {Address} from './address';
import {Observable} from 'rxjs';


export class User {
  id: number;
  password: string;
  newPassword: string;
  email: string;
  adress: Address;
  role: string;
  photo: Observable<any>;


}
