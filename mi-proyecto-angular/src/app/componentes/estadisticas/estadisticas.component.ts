import { Component } from '@angular/core';
import { PuntuacionService } from '../../servicios/puntuacion.service';
import { CommonModule } from '@angular/common';
import { ConfiguracionService } from '../../servicios/configuracion.service';

@Component({
  selector: 'app-estadisticas',
  standalone: true,
  templateUrl: './estadisticas.component.html',
  styleUrl: './estadisticas.component.css',
  imports: [CommonModule]
})
export class EstadisticasComponent {
  ranking: any[] = [];
  sorteoCerrado = false;
  mensaje = '';

  constructor(private estadisticasService: PuntuacionService, private confService: ConfiguracionService) {
    this.estadisticasService.getRanking().subscribe({
      next: (data) => this.ranking = data
    });
    this.confService.getEstadoSorteo().subscribe({
      next: (res) => this.sorteoCerrado = res.cerrado
    });
  }

  cerrarSorteo() {
    if(confirm('¿Estás seguro de que quieres cerrar el sorteo? Esta acción es irreversible')){
      this.confService.cerrarSorteo().subscribe({
        next: (res) => {
          this.sorteoCerrado = true;
          this.mensaje = res.mensaje;
        }
      });
    }
  }
}
