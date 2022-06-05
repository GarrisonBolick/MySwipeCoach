import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AvatarMapPage } from './avatar-map.page';

const routes: Routes = [
  {
    path: '',
    component: AvatarMapPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AvatarMapPageRoutingModule {}
