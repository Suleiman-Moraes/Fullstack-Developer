import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { UserLogado } from '../models/user-logado.model';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private currentUserSubject: BehaviorSubject<UserLogado>;
  public currentUser: Observable<UserLogado>;

  constructor(
    private http: HttpClient
  ) {
    const json = sessionStorage.getItem('tRcr7Ssn') != null ? atob(sessionStorage.getItem('tRcr7Ssn')) : null;
    this.currentUserSubject = new BehaviorSubject<UserLogado>(JSON.parse(json));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): UserLogado {
    return this.currentUserSubject.value;
  }

  login(user): Observable<UserLogado> {
    return this.http.post<any>(`${environment.URL_AUTH_SERVICE_API}/login`, user)
      .pipe(map(response => {
        if (response.data && response.data.token) {
          // this.showTemplate.emit(true);
          sessionStorage.setItem('tRcr7Ssn', btoa(JSON.stringify(response.data)));
          this.currentUserSubject.next(response.data);
          console.log(sessionStorage.getItem('tRcr7Ssn'));
        }
        else {
          // this.showTemplate.emit(false);
        }
        return response.data;
      }));
  }

  logout() {
    sessionStorage.removeItem('tRcr7Ssn');
    // this.showTemplate.emit(false);
    this.currentUserSubject.next(null);
  }
}
