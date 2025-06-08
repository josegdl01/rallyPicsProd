import { Component } from '@angular/core';
import { RouterOutlet, Router } from '@angular/router';
import { NavBarComponent } from './componentes/nav-bar/nav-bar.component';
import { CommonModule } from '@angular/common';
import { FooterComponent } from './componentes/footer/footer.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, NavBarComponent, FooterComponent, CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  constructor(public router: Router) {}

  esLoginYHome(): boolean {
    return this.router.url === '/login' || this.router.url === '/' || this.router.url === '/home';
  }
}
