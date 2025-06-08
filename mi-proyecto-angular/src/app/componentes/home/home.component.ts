import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { GaleriaPublicaComponent } from '../galeria/galeria.component';
import { ConfiguracionService } from '../../servicios/configuracion.service';
import { PuntuacionService } from '../../servicios/puntuacion.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-home',
  imports: [RouterLink, GaleriaPublicaComponent, CommonModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {
  ranking: any[] = [];
  sorteoCerrado = false;

  constructor(private configService: ConfiguracionService, private puntuacionService: PuntuacionService) {}

  ngOnInit() {
    this.configService.getEstadoSorteo().subscribe({
      next: (res) => {
        this.sorteoCerrado = res.cerrado;
        if (this.sorteoCerrado) {
          this.puntuacionService.getRanking().subscribe({
            next: (data) => (this.ranking = data),
            error: () => {
              this.ranking = [];
            }
          });
        }
      },
      error: () => {
        this.sorteoCerrado = false;
      }
    });
  }
}
