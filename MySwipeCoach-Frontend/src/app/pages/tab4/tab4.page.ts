import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ApiService } from 'src/app/services/api.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-tab4',
  templateUrl: './tab4.page.html',
  styleUrls: ['./tab4.page.scss'],
})
export class Tab4Page implements OnInit {

  savedEmail: string = "ChrisDavis@gmail.com"
  emailInput: string = this.savedEmail;
  emailEditMode: boolean = false;

  constructor(
    private auth: AuthService,
    private api: ApiService,
    private router: Router,
  ) { }

  ngOnInit() {
  }

  editEmail() {
    this.emailEditMode = true;
  }

  cancelEmail() {
    this.emailInput = this.savedEmail;
    this.emailEditMode = false;
  }

  submitEmail() {
    this.savedEmail = this.emailInput;
    this.emailEditMode = false;
  }

  async logout() {
    this.api.currentProfile.next(null);
    await this.auth.logout();
    this.router.navigateByUrl('/', {replaceUrl: true});
  }

}
