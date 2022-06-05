import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-confirm-registration',
  templateUrl: './confirm-registration.page.html',
  styleUrls: ['./confirm-registration.page.scss'],
})
export class ConfirmRegistrationPage implements OnInit {

  constructor(private auth: AuthService, private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.queryParams.subscribe({
      next: params => {
        console.log(params.token);
        const token = params.token;
        this.auth.confirmRegistration(token).subscribe();
      },
      error: err => {
        console.log(err);
      }
    });
  }
}
