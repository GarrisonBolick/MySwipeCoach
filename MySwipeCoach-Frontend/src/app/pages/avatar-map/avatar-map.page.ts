import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { GestureController, ModalController, Platform } from '@ionic/angular';
import { ProfileViewComponent } from 'src/app/components/profile-view/profile-view.component';
import { ApiService } from 'src/app/services/api.service';
import { PotentialMatchService } from 'src/app/services/potential-match.service';
import { Profile } from 'src/app/types';

@Component({
  selector: 'app-avatar-map',
  templateUrl: './avatar-map.page.html',
  styleUrls: ['./avatar-map.page.scss'],
})
export class AvatarMapPage implements OnInit {

  @ViewChild('profileContainer') profileContainerElRef: ElementRef<HTMLDivElement>;
  @ViewChild('outerModal') outerModalElRef: ElementRef<HTMLDivElement>;
  @ViewChild('profileModal') profileModalElRef: ElementRef<HTMLDivElement>;
  @ViewChild('profileView') profileViewEl: ProfileViewComponent;
  @ViewChild('swipeLabel') swipeLabelElRef: ElementRef<HTMLSpanElement>;

  profiles: Profile[] = [];
  selectedProfile: Profile = null;
  profileOpened: boolean = false;
  swiped: boolean = false;

  constructor(private api: ApiService, private potentialMatch: PotentialMatchService, private gestureController: GestureController, private platform: Platform) { }

  ngOnInit() {
    this.api.getProfilesInQueue(this.api.currentProfile.value?.email).subscribe(response => {
      this.profiles = response;
    });
  }

  ngAfterViewInit() {
    const profileModalEl = this.profileModalElRef.nativeElement;
    const swipeLabelEl = this.swipeLabelElRef.nativeElement;
    
    const swipeHorizonalGesture = this.gestureController.create({
      el: profileModalEl,
      gestureName: "horizontalGesture",
      direction: "x",
      threshold: 20,
      onStart: (detail) => {
        profileModalEl.style.transition = '0.5s ease-out';
        swipeLabelEl.style.transition = '0.5s ease-out';
      },
      onMove: (detail) => {
        profileModalEl.style.transform = `translateX(${detail.deltaX}px) rotate(${detail.deltaX / 10}deg)`;
        swipeLabelEl.style.transition = '';
        if (detail.deltaX > 0) {
          swipeLabelEl.textContent = 'LIKE';
          swipeLabelEl.style.color = 'aqua';
          swipeLabelEl.style.left = '1rem';
          swipeLabelEl.style.right = '';
        } else {
          swipeLabelEl.textContent = 'DISLIKE'
          swipeLabelEl.style.color = 'lightcoral';
          swipeLabelEl.style.left = '';
          swipeLabelEl.style.right = '1rem';
        }
        swipeLabelEl.style.opacity = '' + Math.abs(detail.deltaX) / 100;
      },
      onEnd: (detail) => {
        profileModalEl.style.transition = '0.5s ease-out';
        swipeLabelEl.style.transition = '0.5s ease-out';
        swipeLabelEl.style.opacity = '0';

        if (detail.deltaX > 150) {
          profileModalEl.style.transform = `translateX(${+this.platform.width() *  2}px) rotate(${detail.deltaX / 2}deg)`;
          this.outerModalElRef.nativeElement.style.pointerEvents = 'none';
          this.profileContainerElRef.nativeElement.style.opacity = '0';
          this.swipeLike(this.selectedProfile);
          this.swiped = true;
        } else if (detail.deltaX < -150) {
          profileModalEl.style.transform = `translateX(-${+this.platform.width() *  2}px) rotate(${detail.deltaX / 2}deg)`;
          this.outerModalElRef.nativeElement.style.pointerEvents = 'none';
          this.profileContainerElRef.nativeElement.style.opacity = '0';
          this.swipeDislike(this.selectedProfile);
          this.swiped = true;
        } else {
          profileModalEl.style.transform = '';
        }
      }
    }, true);

    swipeHorizonalGesture.enable();
  }

  openProfile(profile: Profile) {
    // Open Profile
    this.selectedProfile = profile;
    this.profileOpened = true;

    // Reset styling
    this.profileViewEl.scrollToTop();
    this.profileContainerElRef.nativeElement.style.opacity = '';
    this.profileModalElRef.nativeElement.style.transition = '';
    this.profileModalElRef.nativeElement.style.transform = '';
    this.profileModalElRef.nativeElement.style.opacity = '';
    this.outerModalElRef.nativeElement.style.pointerEvents = '';
  }

  closeProfile() {
    this.profileOpened = false;
  }

  swipeLike(profile: Profile) {
    console.log('Swiped Like', profile);
  }

  swipeDislike(profile: Profile) {
    console.log('Swiped Dislike', profile)
  }

  onSwipeTransitionEnd(event: Event) {
    if (this.swiped) {
      this.profileModalElRef.nativeElement.style.transition = '';
      this.profileModalElRef.nativeElement.style.opacity = '0';
      this.closeProfile();
      this.swiped = false;
    }
  }

}
