import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ApiService } from './api.service';
import { Profile } from '../types';
import { AuthService } from './auth.service';

const rootUrl: string = 'http://localhost:8081/potentialMatch';

@Injectable({
  providedIn: 'root'
})
export class PotentialMatchService {

  constructor(private http: HttpClient, private api: ApiService, private auth: AuthService) { }

  getClientMatches(email: string) {
    this.http.get(`${rootUrl}/ClientMatches?email=${email}`);
  }

  getCoachMatches(id: number) {
    this.http.get(`${rootUrl}/CoachMatches/${id}`);
  }

  swipeLike(swipedProfile: Profile) {
    const currentProfile = this.api.currentProfile.value;
    let requestBody: any = {};

    if (currentProfile.userType == 'Client' && swipedProfile.userType == 'Coach') {
      requestBody.ClientId = currentProfile.id;
      requestBody.CoachId = swipedProfile.id;
      requestBody.ClientSwiped = 1;
      requestBody.CoachSwiped = 0;
    } else if (currentProfile.userType == 'Coach' && swipedProfile.userType == 'Client') {
      requestBody.ClientId = swipedProfile.id;
      requestBody.CoachId = currentProfile.id;
      requestBody.ClientSwiped = 1;
      requestBody.CoachSwiped = 1;
    } else {
      throw Error('User roles are not compatible. One must be Client and the other is Coach.');
    }

    this.http.post(
      `${rootUrl}/add/${currentProfile.id}`,
      requestBody, 
      {headers: {'Authorization': `Bearer ${this.auth.authToken}`}}
    );
  }

  swipeDislike(swipedProfile: Profile) {
    const currentProfile = this.api.currentProfile.value;
    let requestBody: any = {};

    if (currentProfile.userType == 'Client' && swipedProfile.userType == 'Coach') {
      requestBody.ClientId = currentProfile.id;
      requestBody.CoachId = swipedProfile.id;
      requestBody.ClientSwiped = -1;
      requestBody.CoachSwiped = 0;
    } else if (currentProfile.userType == 'Coach' && swipedProfile.userType == 'Client') {
      requestBody.ClientId = swipedProfile.id;
      requestBody.CoachId = currentProfile.id;
      requestBody.ClientSwiped = 1;
      requestBody.CoachSwiped = -1;
    } else {
      throw Error('User roles are not compatible. One must be Client and the other is Coach.');
    }

    this.http.post(
      `${rootUrl}/add/${currentProfile.id}`,
      requestBody,
      {headers: {'Authorization': `Bearer ${this.auth.authToken}`}}
    );
  }

}
