import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IonContent } from '@ionic/angular';
import { CometChatService } from 'src/app/services/comet-chat.service';
import { ApiService } from '../../services/api.service';
import { Conversation, Message } from '../../types';

@Component({
  selector: 'app-conversation',
  templateUrl: './conversation.page.html',
  styleUrls: ['./conversation.page.scss'],
})
export class ConversationPage implements OnInit {

  conversation: Conversation;
  messageInput: string = '';

  @ViewChild(IonContent) ionContent: IonContent;

  constructor(private api: ApiService, private route: ActivatedRoute, private cometChat: CometChatService) { }

  ngOnInit() {
    const id = parseInt(this.route.snapshot.paramMap.get("id"));
    this.api.getConversation(id).subscribe({
      next: result => {
        this.conversation = result;
      }
    });
  }

  sendMessage() {
    if (this.messageInput === '') {
      return;
    }

    const message: Message = {
      id: 100,
      value: this.messageInput,
      author: this.api.currentProfile.value
    };

    this.conversation.messages.push(message);
    this.api.createMessage(message);
    this.api.updateConversation(this.conversation);
    this.messageInput = '';

    this.ionContent.scrollToBottom(1000);
  }
}
