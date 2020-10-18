import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {UserService} from './user.service';
import {BehaviorSubject} from 'rxjs';
import {User} from '../model/user';
import {map} from 'rxjs/operators';
import {CanActivate} from '@angular/router';
import {Role} from '../../security/model/role';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  // BASE_PATH: 'http://localhost:8080'
  USER_NAME_SESSION_ATTRIBUTE_NAME = 'authenticatedUser';
  TOKEN_SESSION_ATTRIBUTE_NAME = 'authenticatedUserToken';
  USER_DATA_SESSION_ATTRIBUTE_NAME = 'authenticatedUserData';


  public username: string;
  public password: string;
  public user: User;
  public isLoggedIn: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  public roles: Role[];

  constructor(private http: HttpClient, public userService: UserService) {
  }

  // tslint:disable-next-line:typedef
  authenticationService(username: string, password: string) {
    return this.http.get(`http://localhost:8080/basicauth`,
      {headers: {authorization: this.createBasicAuthToken(username, password)}}).pipe(map((res) => {
      this.username = username;
      this.password = password;
      this.registerSuccessfulLogin(username, password);
    }));
  }

  // tslint:disable-next-line:typedef
  createBasicAuthToken(username: string, password: string) {
    return 'Basic ' + window.btoa(username + ':' + password);
  }

  // tslint:disable-next-line:typedef
  registerSuccessfulLogin(username, password) {
    sessionStorage.setItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME, username);
    sessionStorage.setItem(this.TOKEN_SESSION_ATTRIBUTE_NAME, this.createBasicAuthToken(username, password));
    this.userService.getByUsername(username).subscribe(data => {
      this.user = data;
      sessionStorage.setItem(this.USER_DATA_SESSION_ATTRIBUTE_NAME, JSON.stringify(this.user));
      this.isLoggedIn.next(true);
    });
  }

  // tslint:disable-next-line:typedef
  logout() {
    sessionStorage.removeItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME);
    sessionStorage.removeItem(this.TOKEN_SESSION_ATTRIBUTE_NAME);
    sessionStorage.removeItem(this.USER_DATA_SESSION_ATTRIBUTE_NAME);
    this.isLoggedIn.next(false);
    this.username = null;
    this.password = null;
  }

  // tslint:disable-next-line:typedef
  isUserLoggedIn() {
    const user = sessionStorage.getItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME);
    if (user === null) {
      this.isLoggedIn.next(false);
      return false;
    }
    this.isLoggedIn.next(true);
    return true;
  }

  // tslint:disable-next-line:typedef
  getLoggedInUserName() {
    const user = sessionStorage.getItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME);
    if (user === null) {
      return '';
    }
    return user;
  }

  hasPrivilege(privS: string): boolean {
    const user = JSON.parse(sessionStorage.getItem(this.USER_DATA_SESSION_ATTRIBUTE_NAME));
    if (user && user.roleList) {
      for (const role of user.roleList) {
        for (const priv of role.privilegeList) {
          if (priv.name === privS) {
            return true;
          }
        }
      }
    }
    return false;
  }

  userPrivilegeAdm(): boolean {
    this.userService.getRolesByUsername('tavi.zorila@gmail.com').subscribe(data => {
      this.roles = [];
      this.roles = data;
    });
    for (const role of this.roles){
      if (this.roles.length > 0){
        return true;
      }
    }
    return false;
    }

}
