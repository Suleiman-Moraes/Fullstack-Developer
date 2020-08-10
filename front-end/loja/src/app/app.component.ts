import { Component } from '@angular/core';
import { AuthenticationService } from './pages/shared/services/authentication.service';
import { Router } from '@angular/router';
import toastr from "toastr";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  public static showTemplate: boolean = true;

  constructor() { }

  ngOnInit() {
  }

  get getShowTemplate(): boolean {
    return sessionStorage.getItem('tRcr7Ssn') != null;
  }
}
