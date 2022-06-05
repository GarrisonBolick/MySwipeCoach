import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Storage } from '@capacitor/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { ClientDto } from '../types';

const EMAIL_KEY = 'user-email';
const AUTH_KEY = 'auth-token';
const rootUrl = 'http://localhost:8081';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  isAuthenticated: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(null);
  emailToken = '';
  authToken = '';

  constructor(private http: HttpClient) {
    this.loadToken();
  }

  async loadToken(): Promise<{email: string, authToken: string}> {
    const emailToken = await Storage.get({key: EMAIL_KEY});
    const authToken = await Storage.get({key: AUTH_KEY});
    if (emailToken && emailToken.value && authToken && authToken.value) {
      this.emailToken = emailToken.value;
      this.authToken = authToken.value;
      this.isAuthenticated.next(true);
      return {email: emailToken.value, authToken: authToken.value};
    }

    this.isAuthenticated.next(false);
    return null;
  }

  setEmailToken(email: string): Promise<void> {
    this.emailToken = email;
    return Storage.set({key: EMAIL_KEY, value: email});
  }

  setAuthToken(authToken: string): Promise<void> {
    this.authToken = authToken;
    return Storage.set({key: AUTH_KEY, value: authToken});
  }

  authenticate(credentials: {email: string, password: string}): Observable<any> {
    return this.http.post<any>(`${rootUrl}/authenticate`, credentials);
  }

  signUp(credentials: {email: string, password: string, userType: string}) {
    return this.http.post(`${rootUrl}/user/registration`, credentials, {responseType: 'text'});
  }

  login(credentials: {email: string, password: string, userType: string}): Observable<ClientDto> {
    return this.http.post<ClientDto>(`${rootUrl}/user/login`, credentials);
  }

  confirmRegistration(token: string): Observable<any> {
    return this.http.get(`${rootUrl}/registrationConfirm?token=${token}`);
  }

  async logout() {
    this.isAuthenticated.next(false);
    const removePromises = [];
    removePromises.push(Storage.remove({key: EMAIL_KEY}));
    removePromises.push(Storage.remove({key: AUTH_KEY}));
    return await Promise.all(removePromises);
  }
}
