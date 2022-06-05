import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { Conversation, Message, Profile } from '../types';
import { HttpClient } from '@angular/common/http';
import { AuthService } from './auth.service';

const rootUrl: string = 'http://localhost:8081';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  currentProfile: BehaviorSubject<Profile> = new BehaviorSubject(null);

  constructor(private http: HttpClient, private auth: AuthService) { }

  getAllProfiles(): Observable<Profile[]> {
    // TEMPORARY: Fake Data
    return of(profiles);

    return this.http.get<Profile[]>(`${rootUrl}/client/all`);
  }

  getMatchedProfiles(email: string): Observable<Profile[]> {
    // TEMPORARY: Fake Data
    return of(profiles.filter((profile) => profile.id != 0));

    return this.http.get<Profile[]>(`${rootUrl}/ClientMatches?email=${email}`);
  }

  getProfilesInQueue(email: string): Observable<Profile[]> {
    // TEMPORARY: Fake Data
    return of(profiles);

    return this.http.get<Profile[]>(
      `${rootUrl}/potentialMatch/ClientMatches?email=${email}`, 
      {headers: {'Authorization': `Bearer ${this.auth.authToken}`}}
    );
  }

  getClientProfile(email: string): Observable<Profile> {
    // TEMPORARY: Fake Data
    return of(profiles[0]);

    return this.http.get<Profile>(
      `${rootUrl}/client?email=${email}`,
      {headers: {'Authorization': `Bearer ${this.auth.authToken}`}}
    );
  }

  getCoachProfile(email: string): Observable<Profile> {
    // TEMPORARY: Fake Data
    return of(profiles[0]);

    return this.http.get<Profile>(
      `${rootUrl}/coach?email=${email}`,
      {headers: {'Authorization': `Bearer ${this.auth.authToken}`}}
    );
  }

  createProfile(profile: Profile): Observable<Profile> {
    // TEMPORARY: Fake Data
    return of(profile);

    return this.http.post<Profile>(`${rootUrl}/client/add`, profile);
  }

  updateProfile(profile: Profile): Observable<Profile> {
    // TEMPORARY: Fake Data
    return of(profile);

    return this.http.post<Profile>(`${rootUrl}/client/update` + profile.id, profile);
  }

  getAllConversations(): Observable<Conversation[]> {
    // TEMPORARY: Fake Data
    return of(conversations);
  }

  getConversationsForProfile(profileId: number): Observable<Conversation[]> {
    // TEMPORARY: Fake Data
    return of(conversations);
  }

  getConversation(id: number): Observable<Conversation> {
    // TEMPORARY: Fake Data
    return of(conversations[id]);
  }

  createConversation(conversation: Conversation): Observable<Conversation> {
    // TEMPORARY: Fake Data
    conversations.push(conversation);
    return of(conversation);
  }

  updateConversation(conversation: Conversation): Observable<Conversation> {
    console.log("Updated Conversation");
    return of(conversation);
  }

  deleteConversation(id: number): Observable<Conversation> {
    console.log("Deleted Conversation");
    return of(conversations.find((c) => c.id == id));
  }

  getMessagesInConversation(conversationId: number): Observable<Message[]> {
    // TEMPORARY: Fake Data
    return of(conversations[conversationId].messages);
  }

  createMessage(message: Message): Observable<Message> {
    console.log("Created Message");
    return of(message);
  }

  updateMessage(message: Message): Observable<Message> {
    console.log("Update Message");
    return of(message);
  }

  deleteMessage(id: number): Observable<Message> {
    console.log("Deleted Message");

    // TEMPORARY: Fake Data
    for (let conversation of conversations) {
      for (let message of conversation.messages) {
        if (message.id == id) {
          return of(message);
        }
      }
    }
    throw Error("Message not found");
  }
}

const profiles: Profile[] = [
  {
    id: 0,
    firstName: "Christopher",
    lastName: "Davis",
    gender: "Male",
    age: 28,
    birthdate: new Date(),
    description: "Twitter expert. Certified web fanatic. Infuriatingly humble problem solver. Typical social media fan.",
    imageURL: "https://www.fakepersongenerator.com/Face/male/male20161083856634643.jpg",
    meetingPreference: "Remote"
  },
  {
    id: 1,
    firstName: "Kristen",
    lastName: "Toledo",
    gender: "Female",
    age: 24,
    birthdate: new Date(),
    description: "Music aficionado. Amateur thinker. Wannabe travel specialist. Devoted web fan. Professional bacon lover.",
    imageURL: "https://www.fakepersongenerator.com/Face/female/female20171026200116944.jpg",
    meetingPreference: "Remote/Non-remote"
  },
  {
    id: 2,
    firstName: "Edward",
    lastName: "Petrucci",
    gender: "Male",
    age: 33,
    birthdate: new Date(),
    description: "Proud reader. Beer enthusiast. Twitter trailblazer. Infuriatingly humble analyst.",
    imageURL: "https://www.fakepersongenerator.com/Face/male/male20161083801247956.jpg",
    meetingPreference: "Non-remote"
  },
  {
    id: 3,
    firstName: "Elizabeth",
    lastName: "Sanchez",
    gender: "Female",
    age: 23,
    birthdate: new Date(),
    description: "Coffee junkie. Passionate internet lover. Analyst. Certified food specialist. Avid problem solver. The best teacher is the one who suggests rather than dogmatizes.",
    imageURL: "https://www.fakepersongenerator.com/Face/female/female1021483839386.jpg",
    meetingPreference: "Remote"
  }
];

const conversations: Conversation[] = [
  {
    id: 0,
    contacts: [profiles[0], profiles[1]],
    messages: [
      {
        id: 0,
        value: "Hello",
        author: profiles[0]
      },
      {
        id: 1,
        value: "How are you?",
        author: profiles[0]
      },
      {
        id: 2,
        value: "Hi!",
        author: profiles[1]
      },
      {
        id: 3,
        value: "I'm good. Thanks!",
        author: profiles[1]
      }
    ]
  },
  {
    id: 1,
    contacts: [profiles[0], profiles[2]],
    messages: [
      {
        id: 4,
        value: "Wanna meet up?",
        author: profiles[0]
      },
      {
        id: 5,
        value: "I would like to, but I've been really busy. I've been having to work overtime all the time. I'm so tired.",
        author: profiles[2]
      }
    ]
  },
  {
    id: 2,
    contacts: [profiles[0], profiles[3]],
    messages: [
      {
        id: 6,
        value: "I like apples.",
        author: profiles[0]
      },
      {
        id: 7,
        value: "Me too!",
        author: profiles[3]
      },
      {
        id: 8,
        value: "That's great!",
        author: profiles[0]
      }
    ]
  }
];