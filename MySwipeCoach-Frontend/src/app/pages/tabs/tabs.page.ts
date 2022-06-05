import { Component } from '@angular/core';
import { Subscription } from 'rxjs';
import { ApiService } from 'src/app/services/api.service';
import { AuthService } from 'src/app/services/auth.service';
import { CometChatService } from 'src/app/services/comet-chat.service';

@Component({
  selector: 'app-tabs',
  templateUrl: 'tabs.page.html',
  styleUrls: ['tabs.page.scss']
})
export class TabsPage {

  currentProfileSubscription: Subscription;

  constructor(private api: ApiService, private auth: AuthService, private cometChat: CometChatService) {}

  ngOnInit() {
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
  }

}
