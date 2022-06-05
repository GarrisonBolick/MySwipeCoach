import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Storage } from '@capacitor/core';
import { AlertController, LoadingController } from '@ionic/angular';
import { ApiService } from 'src/app/services/api.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage implements OnInit {

  credentials: FormGroup;

  constructor(
    private auth: AuthService,
    private api: ApiService,
    private router: Router,
    private fb: FormBuilder,
    private alertController: AlertController,
    private loadingController: LoadingController
  ) { }

  ngOnInit() {
    this.credentials = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]]
    });
  }

  async login() {
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
      }

      await alert.present();
      return;
    }

    // Login
    this.auth.login(this.credentials.value).subscribe({
      next: async profile => {
        console.log("Log in success:", profile);
        await loading.dismiss();
        
        this.api.currentProfile.next(profile);

        this.auth.setEmailToken(profile.email);
        this.auth.authenticate(this.credentials.value).subscribe(async response => {
          await this.auth.setAuthToken(response.token);
          await this.auth.loadToken();
          this.router.navigateByUrl('/tabs', {replaceUrl: true});
        });
      },
      error: async result => {
        console.log("Log in failed")
        await loading.dismiss();
        const alert = await this.alertController.create({
          header: 'Login failed',
          message: 'Invalid email or password<br/>OR confirm your email',
          buttons: ['OK']
        });

        await alert.present();
      }
    })
  }
  
  deleteTokens() {
    Storage.clear().then(() => {
      this.router.navigateByUrl('/intro', {replaceUrl: true});
    });
  }

  async bypassLogin() {
    await this.auth.setEmailToken('test@test.com');
    await this.auth.setAuthToken('test');
    await this.auth.loadToken();

    // TEMPORARY: Fake Data
    this.api.currentProfile.next({
      id: 0,
      firstName: "Christopher",
      lastName: "Davis",
      gender: "Male",
      age: 28,
      birthdate: new Date(),
      description: "Twitter expert. Certified web fanatic. Infuriatingly humble problem solver. Typical social media fan.",
      imageURL: "https://www.fakepersongenerator.com/Face/male/male20161083856634643.jpg",
      meetingPreference: "Remote"
    });
    
    this.router.navigateByUrl('/tabs', {replaceUrl: true});
  }
}
