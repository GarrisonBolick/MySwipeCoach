import { Injectable } from '@angular/core';
import { CometChat } from '@cometchat-pro/cordova-ionic-chat';

const appID = "1940230e001bbe2c";
const region = "us";
const authKey = "dedf018b677f11ff8cf7df697ced7b9521f9b928";

@Injectable({
  providedIn: 'root'
})
export class CometChatService {

  constructor() {
    const appSetting = new CometChat.AppSettingsBuilder()
      .subscribePresenceForAllUsers()
      .setRegion(region)
      .build();
    CometChat.init(appID, appSetting).then(
      () => {
        console.log("CometChat initialization completed successfully");
        // You can now call login function.
      },
      (error) => {
        console.log("CometChat initialization failed with error:", error);
        // Check the reason for error and take appropriate action.
      }
    );
  }

  registerUser(uid: string | number, name: string): Promise<CometChat.User> {
    // CometChat
    var user = new CometChat.User(`${uid}`);
    user.setName(name);

    return CometChat.createUser(user, authKey);
  }

  loginUser(uid: string | number): Promise<CometChat.User> {
    return CometChat.login(`${uid}`, authKey);
  }

  logoutUser(): Promise<Object> {
    console.log('CometChat logout');
    return CometChat.logout();
  }

  getCurrentUser(): Promise<CometChat.User> {
    return CometChat.getLoggedinUser();
  }

  getUser(uid: string | number): Promise<CometChat.User> {
    return CometChat.getUser(typeof uid == "string" ? uid : uid.toString());
  }

  sendMessage(receiverId: string | number, message: string): Promise<CometChat.BaseMessage> {
    let receiverType = CometChat.RECEIVER_TYPE.USER;
    let textMessage = new CometChat.TextMessage(`${receiverId}`, message, receiverType);

    return CometChat.sendMessage(textMessage);
  }

  addMessageListener(uid: string | number) {
    console.log('Add message listener', uid)
    CometChat.addMessageListener(
      `${uid}`,
      new CometChat.MessageListener({
        onTextMessageReceived: textMessage => {
          console.log("Text message received successfully", textMessage);
        },
        onMediaMessageReceived: mediaMessage => {
          console.log("Media message received successfully", mediaMessage);
        },
        onCustomMessageReceived: customMessage => {
          console.log("Custom message received successfully", customMessage);
        }
      })
    );
  }

  removeMessageListener(uid: string | number) {
    console.log('Remove message listener', uid);
    CometChat.removeMessageListener(`${uid}`);
  }

  async getMissedMessages(uid: string | number): Promise<CometChat.BaseMessage[]> {
    let limit = 30;
    let latestId = await CometChat.getLastDeliveredMessageId();

    var messagesRequest = new CometChat.MessagesRequestBuilder()
                            .setUID(`${uid}`)
                            .setMessageId(latestId)
                            .setLimit(limit)
                            .build();

    return messagesRequest.fetchNext();
  }

  getMessageHistory(uid: string | number): Promise<CometChat.BaseMessage[]> {
    let limit = 30;
    let messagesRequest = new CometChat.MessagesRequestBuilder()
                            .setUID(`${uid}`)
                            .setLimit(limit)
                            .build();

    return messagesRequest.fetchPrevious();
  }

  getConversations(): Promise<CometChat.Conversation[]> {
    let limit = 30;
    let conversationRequest = new CometChat.ConversationsRequestBuilder()
                              .setLimit(limit)
                              .build();

    return conversationRequest.fetchNext();
  }

  getConversation(uid: string | number): Promise<CometChat.Conversation> {
    return CometChat.getConversation(`${uid}`, 'user');
  }
}
