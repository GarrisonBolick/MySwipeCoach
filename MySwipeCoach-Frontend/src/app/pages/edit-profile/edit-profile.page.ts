import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { ApiService } from '../../services/api.service';
import { Profile } from '../../types';

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.page.html',
  styleUrls: ['./edit-profile.page.scss'],
})
export class EditProfilePage implements OnInit {

  profile: Profile;

  constructor(private api: ApiService, private modalController: ModalController) { }

  ngOnInit() {
    this.profile = Object.create(this.api.currentProfile.value);
    console.log(this.profile)
  }

  dismiss() {
    this.modalController.dismiss();
  }

  save() {
    console.log(this.profile);
    this.api.currentProfile.next(this.profile);
    this.api.updateProfile(this.profile).subscribe(result => {
      this.modalController.dismiss(this.profile);
    })
  }

  setfirstName(firstname: string) {
    this.profile.firstName = firstname;
  }

  setlastName(lastname: string) {
    this.profile.lastName = lastname;
  }

  setGender(gender: string) {
    this.profile.gender = gender;
  }

  setBirthdate(birthdate: string) {
    this.profile.birthdate = new Date(birthdate);
  }

  setDescription(description: string) {
    this.profile.description = description;
  }

}
