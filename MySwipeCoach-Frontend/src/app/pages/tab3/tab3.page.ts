import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ApiService } from '../../services/api.service';
import { Conversation, Profile } from '../../types';

@Component({
  selector: 'app-tab3',
  templateUrl: 'tab3.page.html',
  styleUrls: ['tab3.page.scss']
})
export class Tab3Page {

  matchedProfiles: Profile[];
  conversations: Conversation[];

  constructor(private api: ApiService, private router: Router) {}

  ngOnInit() {
    this.api.getMatchedProfiles(this.api.currentProfile.value.email).subscribe({
      next: result => {
        this.matchedProfiles = result;
      }
    });

    // TEMPORARY: Fake Data
    this.api.getConversationsForProfile(0).subscribe({
      next: result => {
        this.conversations = result;
      }
    });
  }

  onConversationClick(id: number) {
    console.log("Clicked id: " + id);
  }

  onContactClick(contact: Profile) {
    let conversation = this.conversations.find(c => {
      const currentProfile = this.api.currentProfile.value;
      return c.contacts.some((profile) => profile.id == currentProfile.id)
        && c.contacts.some((profile) => profile.id == contact.id && contact.id != currentProfile.id);
    });

    if (conversation === undefined) {
      this.api.createConversation({
        id: 100,
        contacts: [this.api.currentProfile.value, contact],
        messages: []
      }).subscribe(result => {
        conversation = result;
        this.router.navigate(['tabs', 'tab3', 'conversations', conversation.id]);
      })
    } else {
      this.router.navigate(['tabs', 'tab3', 'conversations', conversation.id]);
    }
  }

}
