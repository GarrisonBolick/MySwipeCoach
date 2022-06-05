import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AlertController, LoadingController } from '@ionic/angular';
import { CometChatService } from 'src/app/services/comet-chat.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.page.html',
  styleUrls: ['./signup.page.scss'],
})
export class SignupPage implements OnInit {

  credentials: FormGroup;

  constructor(
    private auth: AuthService,
    private router: Router,
    private fb: FormBuilder,
    private alertController: AlertController,
    private loadingController: LoadingController,
    private cometChat: CometChatService
  ) { }

  ngOnInit() {
    this.credentials = this.fb.group({
      'email': ['', [Validators.required, Validators.email]],
      'password': ['', [Validators.required]],
      'confirmPassword': ['', [Validators.required]],
      'userType': ['Client', Validators.required]
    });
    this.credentials.setValidators(this.checkPasswords);
  }

  checkPasswords(formGroup: FormGroup) {
    const password = formGroup.get('password').value;
    const confirmPassword = formGroup.get('confirmPassword').value;

    return password === confirmPassword ? null : {notSame: true};
  }

  async signUp() {
    const loading = await this.loadingController.create();
    await loading.present();

    // Validation alert
    if (this.credentials.invalid) {
      loading.dismiss();
      const alert = await this.alertController.create({
        header: 'Sign up failed',
        buttons: ['OK']
      });

      // Email
      if (this.credentials.hasError('required', 'email') || this.credentials.hasError('email', 'email')) {
        alert.message = 'Please enter a valid email';
      // Password
      } else if (this.credentials.hasError('required', 'password')) {
        alert.message = 'Please enter a password';
      } else if (this.credentials.hasError('notSame')) {
        alert.message = 'Passwords do not match';
      }

      await alert.present();
      return;
    }

    // Sign up
    this.auth.signUp(this.credentials.value).subscribe({
      next: async result => {
        console.log('Signed up user', this.credentials.value.email);
        await loading.dismiss();
        const alert = await this.alertController.create({
          header: 'Signed up successfully!',
          message: 'Please check your email to validate your account before you login.',
          buttons: ['OK']
        });
        await alert.present();
        // this.auth.authenticate(this.credentials.value).subscribe(async response => {
        // });

        // this.cometChat.registerUser(result.email, `${profile.firstName} ${profile.lastName}`);

        // await this.router.navigateByUrl('/create-profile', {replaceUrl: true});
      },
      error: async result => {
        await loading.dismiss();
        const error = JSON.parse(result.error);
        const alert = await this.alertController.create({
          header: error.error,
          message: error.message,
          buttons: ['OK']
        });

        await alert.present();
      }
    })
  }

}
