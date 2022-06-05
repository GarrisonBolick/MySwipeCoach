import { Component } from '@angular/core';
import { Profile } from '../../types';
import { AlertController, ModalController } from '@ionic/angular';
import { CameraOptions, CameraResultType, CameraSource, Plugins } from '@capacitor/core';
import { EditProfilePage } from '../edit-profile/edit-profile.page';
import { ApiService } from '../../services/api.service';

const { Camera } = Plugins;

@Component({
  selector: 'app-tab2',
  templateUrl: 'tab2.page.html',
  styleUrls: ['tab2.page.scss']
})
export class Tab2Page {

  profile: Profile;

  constructor(private api: ApiService, private alertController: AlertController, private modalController: ModalController) {
    console.log('MY PROFILE', this.profile);
    this.profile = this.api.currentProfile.value;
  }

  async editProfile() {
    const modal = await this.modalController.create({
      component: EditProfilePage
    });

    modal.onDidDismiss()
      .then((data) => {
        console.log(data);
        if (data['data'] != undefined) {
          this.profile = data['data']; // Here's your selected user!
        }
    });

    return await modal.present();
  }

  async editAbout() {
    const alert = await this.alertController.create({
      header: 'About',
      buttons: ['Cancel', 'Save'],
      inputs: [
        {
          name: 'About',
          value: this.profile.description,
          type: 'textarea',
          placeholder: 'About',
          attributes: {
            rows: 4
          }
        }
      ]
    });

    await alert.present();

    const { role } = await alert.onDidDismiss();
    console.log('onDidDismiss resolved with role', role);
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
              this.api.updateProfile(this.profile);
            });
          }
        },
        {
          text: "Gallery",
          handler: () => {
            Camera.getPhoto(galleryOptions)
            .then((photo) => {
              this.profile.imageURL = photo.dataUrl;
              this.api.updateProfile(this.profile);
            });
          }
        }
      ]
    });

    await alert.present();
  }

  editMeetingPreference(value: string) {
    console.log("Meeting Preference: " + value);
    this.profile.meetingPreference = value;
    this.api.updateProfile(this.profile);
  }
}
