import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PuntuacionService } from '../../servicios/puntuacion.service';
import { Puntuacion } from '../../modelos/puntuacion';
import { CommonModule } from '@angular/common';

@Component({
  standalone: true,
  selector: 'app-listado-puntuaciones',
  templateUrl: './listado-puntuaciones.component.html',
  styleUrls: ['./listado-puntuaciones.component.css'],
  imports: [CommonModule]
})
export class ListadoPuntuacionesComponent implements OnInit {
  puntuaciones: Puntuacion[] = [];
  publicacionId: number = 0;

  constructor(
    private route: ActivatedRoute,
    private puntuacionService: PuntuacionService
  ) {}

  ngOnInit() {
    this.publicacionId = Number(this.route.snapshot.params['id']);
    this.puntuacionService.getPuntuacionesByPublicacion(this.publicacionId).subscribe({
      next: (data) => this.puntuaciones = data,
      error: () => this.puntuaciones = []
    });
  }
}
