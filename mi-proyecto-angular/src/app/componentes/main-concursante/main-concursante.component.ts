import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { CredencialesService } from '../../servicios/credenciales.service';
import { ConcursanteService } from '../../servicios/concursante.service';
import { Concursante } from '../../modelos/concursante';

@Component({
  selector: 'app-main-concursante',
  imports: [RouterLink],
  templateUrl: './main-concursante.component.html',
  styleUrl: './main-concursante.component.css'
})
export class MainConcursanteComponent {

  public concursante: Concursante = <Concursante>{};

  constructor(
    private credencialesService: CredencialesService,
    private concursanteService: ConcursanteService,
    private router: Router
  ) {
    console.log("Se recibe el ID: " + this.credencialesService.getDecodedId());
    this.concursanteService.getConcursanteById(this.credencialesService.getDecodedId()).subscribe({
      next: (data) => {
        console.log("Concursante id: ", data);
        this.concursante = data;
      }
    })
  }
}
