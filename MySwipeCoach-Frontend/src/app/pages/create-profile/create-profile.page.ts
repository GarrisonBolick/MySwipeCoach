import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { Camera, CameraOptions, CameraResultType, CameraSource } from '@capacitor/core';
import { AlertController, IonSlides } from '@ionic/angular';
import { ApiService } from 'src/app/services/api.service';
import { AuthService } from 'src/app/services/auth.service';
import { CometChatService } from 'src/app/services/comet-chat.service';
import { Profile } from 'src/app/types';

@Component({
  selector: 'app-create-profile',
  templateUrl: './create-profile.page.html',
  styleUrls: ['./create-profile.page.scss'],
})
export class CreateProfilePage implements OnInit {

  profile: Profile = {imageURL: "https://thumbs.dreamstime.com/b/default-avatar-profile-vector-user-profile-default-avatar-profile-vector-user-profile-profile-179376714.jpg"};

  @ViewChild(IonSlides) slides: IonSlides;

  constructor(
    private api: ApiService,
    private auth: AuthService,
    private router: Router,
    private alertController: AlertController,
    private cometChat: CometChatService
  ) { }

  ngOnInit() {
  }

  async selectImageSource() {
    const cameraOptions: CameraOptions = {
      quality: 100,
      resultType: CameraResultType.DataUrl,
      height: 200,
      correctOrientation: true,
      source: CameraSource.Camera
    };
    const galleryOptions: CameraOptions = {
      quality: 100,
      resultType: CameraResultType.DataUrl,
      height: 200,
      correctOrientation: true,
      source: CameraSource.Photos
    };
    const alert = await this.alertController.create({
      header: "Select Source",
      message: "Pick a source for your image",
      buttons: [
        {
          text: "Camera",
          handler: () => {
            Camera.getPhoto(cameraOptions)
            .then((photo) => {
              this.profile.imageURL = photo.dataUrl;
            });
          }
        },
        {
          text: "Gallery",
          handler: () => {
            Camera.getPhoto(galleryOptions)
            .then((photo) => {
              this.profile.imageURL = photo.dataUrl;
            });
          }
        }
      ]
    });

    await alert.present();
  }

  next() {
    this.slides.slideNext();
  }
  
  start() {
    this.cometChat.registerUser(this.profile.email, `${this.profile.firstName} ${this.profile.lastName}`);

    this.api.createProfile(this.profile);
    this.api.currentProfile.next(this.profile);
    this.router.navigateByUrl('/tabs');
  }

  printProfile() {
    console.log(this.profile);
  }
  
  setBirthdate(birthdate: string) {
    this.profile.birthdate = new Date(birthdate);
  }

}
