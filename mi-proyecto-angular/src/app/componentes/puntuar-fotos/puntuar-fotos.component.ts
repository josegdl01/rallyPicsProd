import { Component, OnInit } from '@angular/core';
import { PublicacionService } from '../../servicios/publicacion.service';
import { PuntuacionService } from '../../servicios/puntuacion.service';
import { CredencialesService } from '../../servicios/credenciales.service';
import { ConfiguracionService } from '../../servicios/configuracion.service';
import { Publicacion64 } from '../../modelos/publicacion64';
import { Puntuacion } from '../../modelos/puntuacion';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-puntuar-fotos',
  templateUrl: './puntuar-fotos.component.html',
  styleUrls: ['./puntuar-fotos.component.css'],
  imports: [CommonModule]
})
export class PuntuarFotosComponent implements OnInit {
  publicaciones: Publicacion64[] = [];
  juezId: number = 0;
  puntuaciones: Puntuacion[] = [];
  mensajeError: string = '';
  sorteoCerrado = false;

  constructor(
    private publicacionService: PublicacionService,
    private puntuacionService: PuntuacionService,
    private credencialesService: CredencialesService,
    private configuracionService: ConfiguracionService
  ) {}

  ngOnInit() {
    this.juezId = this.credencialesService.getDecodedId();

    this.configuracionService.getEstadoSorteo().subscribe({
      next: (res) => {
        this.sorteoCerrado = res.cerrado;
      }
    });

    this.publicacionService.getPublicacionesValidadas().subscribe({
      next: (publicaciones: any[]) => {
        this.puntuacionService.getPuntuacionesByJuez(this.juezId).subscribe({
          next: (puntuaciones: Puntuacion[]) => {
            this.puntuaciones = puntuaciones;
            const puntuadasIds = puntuaciones.map(p => p.publicacion.id);
            this.publicaciones = publicaciones
              .filter(pub => pub.validada)
              .filter(pub => !puntuadasIds.includes(pub.id))
              .map(pub => ({
                ...pub,
                foto: pub.fotoBase64
              }));
          }
        });
      },
      error: (err) => {
        if (err.status === 404) {
          this.mensajeError = 'No hay fotos disponibles para puntuar.';
        } else {
          this.mensajeError = 'Error al cargar las fotos.';
        }
      }
    });
  }

  puntuar(publicacion: any, nota: any) {
    if (this.sorteoCerrado) {
      alert('El sorteo está cerrado. Ya no se pueden puntuar fotos.');
      return;
    }
    const notaNum = Number(nota);
    if (isNaN(notaNum) || notaNum < 1 || notaNum > 10) {
      alert('Introduce una puntuación válida entre 1 y 10');
      return;
    }
    const nuevaPuntuacion: Puntuacion = {
      juez: { id: this.juezId },
      publicacion: { id: publicacion.id },
      nota: notaNum
    };
    this.puntuacionService.insertarPuntuacion(nuevaPuntuacion).subscribe({
      next: () => {
        this.publicaciones = this.publicaciones.filter(p => p.id !== publicacion.id);
      },
      error: () => {
        alert('Error al puntuar la foto');
      }
    });
  }
}
