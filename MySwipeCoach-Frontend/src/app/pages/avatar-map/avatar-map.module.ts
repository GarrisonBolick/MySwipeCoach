import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { AvatarMapPageRoutingModule } from './avatar-map-routing.module';

import { AvatarMapPage } from './avatar-map.page';
import { ProfileViewComponent } from 'src/app/components/profile-view/profile-view.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    AvatarMapPageRoutingModule
  ],
  declarations: [
    AvatarMapPage,
    ProfileViewComponent
  ]
})
export class AvatarMapPageModule {}
