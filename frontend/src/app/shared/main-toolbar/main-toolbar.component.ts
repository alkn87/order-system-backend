import { Component, OnInit } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';

@Component({
  selector: 'app-main-toolbar',
  templateUrl: './main-toolbar.component.html',
  styleUrls: ['./main-toolbar.component.scss']
})
export class MainToolbarComponent implements OnInit {

  isAuthenticated: boolean = false;

  constructor(private authenticationService: KeycloakService) { }

  ngOnInit(): void {
    if (!this.authenticationService.isLoggedIn().then(value => this.isAuthenticated = value)) {
      this.isAuthenticated = false;
    }
  }

  logout(): void {
    this.authenticationService.logout();
  }

}
