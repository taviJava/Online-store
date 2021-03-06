import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../service/user.service';
import {User} from '../../model/user';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Address} from '../../model/address';
import {HttpEventType, HttpResponse} from '@angular/common/http';

@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.css']
})
export class UserEditComponent implements OnInit {
  user: User;
  id: number;
  myGroup: FormGroup;
  address: Address;
  selectedFiles: FileList;
  currentFile: File;
  progress = 0;
  message = '';
  matched = true;
  confirmPassword = '';

  constructor(private route: ActivatedRoute,
              private router: Router,
              private userService: UserService) {
  }

  ngOnInit(): void {
    this.user = new User();
    this.id = this.route.snapshot.params.id;
    this.userService.getById(this.id).subscribe(data => {
      this.user = data;
    });
    this.myGroup = new FormGroup({
      email: new FormControl(),
      password: new FormControl(),
      newPassword: new FormControl(),
      street: new FormControl(),
      city: new FormControl(),
      country: new FormControl(),
      zipcode: new FormControl(),
      confirmPassword: new FormControl()
    });
  }
  // tslint:disable-next-line:typedef
  matchPasswords() {
    this.confirmPassword = this.myGroup.get('confirmPassword').value;
    if (this.user.newPassword === '' || this.user.newPassword === this.confirmPassword) {
      this.matched = true;
    } else {
      this.matched = false;
    }
  }

  // tslint:disable-next-line:typedef
  goToUser() {
    this.router.navigate(['users']);
  }
  // tslint:disable-next-line:typedef
  onSubmit() {
    this.user.email = this.myGroup.get('email').value;
    this.user.password = this.myGroup.get('password').value;
    this.address.street = this.myGroup.get('street').value;
    this.address.city = this.myGroup.get('city').value;
    this.address.zipCode = this.myGroup.get('zipcode').value;
    this.address.country = this.myGroup.get('country').value;
    this.user.adress = this.address;
    this.userService.update(this.user).subscribe(result => {
    });
    this.upload();
    setTimeout(() =>
      {
        this.router.navigate(['users']);
      },
      5000);
  }
  // tslint:disable-next-line:typedef
  selectFile(event) {
    this.selectedFiles = event.target.files;
  }

  // tslint:disable-next-line:typedef
  upload() {
    this.progress = 0;
    this.currentFile = this.selectedFiles.item(0);
    this.userService.upload(this.currentFile).subscribe(
      event => {
        if (event.type === HttpEventType.UploadProgress) {
          this.progress = Math.round(100 * event.loaded / event.total);
        } else if (event instanceof HttpResponse) {
          this.message = event.body.message;
          const  a = event.body.id;
        }
      },
      err => {
        this.progress = 0;
        this.message = 'Could not upload the file!';
        this.currentFile = undefined;
      });

    this.selectedFiles = undefined;
  }
}
