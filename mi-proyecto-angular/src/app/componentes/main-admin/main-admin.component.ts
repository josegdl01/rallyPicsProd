import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { GaleriaPublicaComponent } from "../galeria/galeria.component";

@Component({
  selector: 'app-main-admin',
  imports: [RouterLink, GaleriaPublicaComponent],
  templateUrl: './main-admin.component.html',
  styleUrl: './main-admin.component.css'
})
export class MainAdminComponent {

}
