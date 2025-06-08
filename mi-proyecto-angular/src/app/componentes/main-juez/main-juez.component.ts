import { Component } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Juez } from '../../modelos/juez';
import { JuezService } from '../../servicios/juez.service';
import { CredencialesService } from '../../servicios/credenciales.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-main-juez',
  imports: [RouterLink, CommonModule],
  templateUrl: './main-juez.component.html',
  styleUrl: './main-juez.component.css'
})
export class MainJuezComponent {
  
  public juez: Juez = <Juez>{};

  constructor(private router: Router, private activatedRoute: ActivatedRoute, private credencialesService: CredencialesService, private juezService: JuezService) {
    console.log("Se recibe el ID: " + this.credencialesService.getDecodedId());
    this.juezService.getJuezById(this.credencialesService.getDecodedId()).subscribe({
      next: (data) => {
        console.log("Juez id: ", data);
        this.juez = data;
      }
    })
  }
}
