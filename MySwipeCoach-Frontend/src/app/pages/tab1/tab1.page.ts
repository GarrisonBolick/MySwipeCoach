import { Component, ElementRef, QueryList, ViewChild, ViewChildren } from '@angular/core';
import { Profile } from '../../types';
import { GestureController, GestureDetail, Platform } from '@ionic/angular';
import { ApiService } from '../../services/api.service';
import { AuthService } from '../../services/auth.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-tab1',
  templateUrl: 'tab1.page.html',
  styleUrls: ['tab1.page.scss']
})
export class Tab1Page {

  profiles: Profile[];
  index = 0;
  detail: GestureDetail;
  showingDescription = false;
  moving = false;
  currentProfileSubscription: Subscription;

  @ViewChild('swipeLabel') swipeLabel: ElementRef;
  @ViewChildren('profileDiv', {read: ElementRef}) profileDivs: QueryList<ElementRef>;

  constructor(private auth: AuthService, private api: ApiService, private gestureController: GestureController, private platform: Platform) {}

  async ngOnInit() {
    this.currentProfileSubscription = this.api.currentProfile.subscribe(response => {
      // TEMPORARY: If bypass login.
      if (this.auth.authToken == 'test') {
        this.api.getProfilesInQueue('test').subscribe({
          next: (response: Profile[]) => {
            this.profiles = response;
          }
        })
      }

      if (response && response.email) {
        this.api.getProfilesInQueue(response.email).subscribe({
          next: (response: Profile[]) => {
            this.profiles = response;
          }
        });
      }
    });
  }

  ngOnDestroy() {
    this.currentProfileSubscription.unsubscribe();
  }

  ngAfterViewInit() {
    this.profileDivs.forEach(profileDivRef => {
      const profileDiv = profileDivRef.nativeElement;

      const swipeHorizonalGesture = this.gestureController.create({
        el: profileDiv,
        gestureName: "horizontalGesture",
        direction: "x",
        threshold: 20,
        onStart: (detail) => {
          profileDiv.style.transition = '0.5s ease-out';
          this.swipeLabel.nativeElement.style.transition = '0.5s ease-out';
        },
        onMove: (detail) => {
          profileDiv.style.transform = `translateX(${detail.deltaX}px) rotate(${detail.deltaX / 10}deg)`;
          this.swipeLabel.nativeElement.style.transition = '';
          if (detail.deltaX > 0) {
            this.swipeLabel.nativeElement.innerText = "LIKE";
            this.swipeLabel.nativeElement.style.color = "aqua";
            this.swipeLabel.nativeElement.style.left = '10px';
            this.swipeLabel.nativeElement.style.right = '';
          } else {
            this.swipeLabel.nativeElement.innerText = "DISLIKE";
            this.swipeLabel.nativeElement.style.color = "lightcoral";
            this.swipeLabel.nativeElement.style.left = '';
            this.swipeLabel.nativeElement.style.right = '10px';
          }
          this.swipeLabel.nativeElement.style.opacity = Math.abs(detail.deltaX) / 100;
        },
        onEnd: (detail) => {
          profileDiv.style.transition = '0.5s ease-out';
          this.swipeLabel.nativeElement.style.transition = '0.5s ease-out';
          this.swipeLabel.nativeElement.style.opacity = '0';

          if (detail.deltaX > 150) {
            profileDiv.style.transform = `translateX(${+this.platform.width() *  2}px) rotate(${detail.deltaX / 2}deg)`;
            this.showingDescription = false;
          } else if (detail.deltaX < -150) {
            profileDiv.style.transform = `translateX(-${+this.platform.width() *  2}px) rotate(${detail.deltaX / 2}deg)`;
            this.showingDescription = false;
          } else {
            profileDiv.style.transform = '';
          }
        }
      }, true);

      const swipeVerticalGesture = this.gestureController.create({
        el: profileDiv,
        gestureName: "swipeVertical",
        direction: "y",
        threshold: 20,
        onStart: (detail) => {
          this.moving = true;
          profileDiv.children[1].style.transition = '0.3s ease-out';
          profileDiv.children[1].children[1].style.opacity = 1;
        },
        onMove: (detail) => {
          // If description is already showing
          if (this.showingDescription) {
            
            // Don't scroll up
            if (detail.deltaY < 0) {
              return;
            }

            // Don't scroll past the bottom
            if (detail.deltaY > profileDiv.children[1].offsetHeight) {
              profileDiv.children[1].style.transform = ``;

            // Move along gesture going down
            } else {
              profileDiv.children[1].style.transform = `translateY(${detail.deltaY - profileDiv.children[1].offsetHeight}px)`;
            }

          // If description is not showing
          } else {

            // Don't scroll down
            if (detail.deltaY > 0) {
              return;
            }

            // Don't scroll past the top
            if (-detail.deltaY > profileDiv.children[1].offsetHeight) {
              profileDiv.children[1].style.transform = `translateY(${-profileDiv.children[1].offsetHeight}px)`;

            // Move along gesture going up
            } else {
              profileDiv.children[1].style.transform = `translateY(${detail.deltaY}px)`;
              profileDiv.children[1].children[0].style.transform = `translateY(${-profileDiv.children[1].offsetHeight}px)`;
            }
          }
        },
        onEnd: (detail) => {
          this.moving = false;

          // Scroll up if reached threshold
          if (detail.deltaY < -50) {
            profileDiv.children[1].style.transform = `translateY(${-profileDiv.children[1].offsetHeight}px)`;
            this.showingDescription = true;
            
          // Scroll down if reached threshold
          } else if (detail.deltaY > 50) {
            profileDiv.children[1].style.transform = ``;
            this.showingDescription = false;
          
          // Scroll up if that was its previous position
          } else if (this.showingDescription === true) {
            profileDiv.children[1].style.transform = `translateY(${-profileDiv.children[1].offsetHeight}px)`;
          
          // Scroll down if that was its previous position
          } else if (this.showingDescription === false) {
            profileDiv.children[1].style.transform = ``;
          }
        }
      }, true);

      swipeVerticalGesture.enable();
      swipeHorizonalGesture.enable();
    })
  }

  descriptionTransitionEnd() {
    if (this.showingDescription === true && this.moving === false || this.showingDescription === false && this.moving === true) {
      return;
    }

    this.profileDivs.forEach(profileDivRef => {
      const profileDiv = profileDivRef.nativeElement;
      profileDiv.children[1].children[0].style.transform = '';
      profileDiv.children[1].children[1].style.opacity = 0;
      this.showingDescription = false;
    })
  }
}
