import { Component } from '@angular/core';
import { Juez } from '../../modelos/juez';
import { Publicacion64 } from '../../modelos/publicacion64';
import { PublicacionService } from '../../servicios/publicacion.service';
import { ActivatedRoute } from '@angular/router';
import { JuezService } from '../../servicios/juez.service';
import { ConfiguracionService } from '../../servicios/configuracion.service'; 
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-fotos-juez',
  imports: [CommonModule],
  templateUrl: './fotos-juez.component.html',
  styleUrl: './fotos-juez.component.css'
})
export class FotosJuezComponent {
  public juez: Juez = <Juez>{};
  public publicaciones: Publicacion64[] = [];
  public sorteoCerrado = false; 

  constructor(
    private publicacionService: PublicacionService,
    private configuracionService: ConfiguracionService 
  ) { 
    this.configuracionService.getEstadoSorteo().subscribe({
      next: (res) => {
        this.sorteoCerrado = res.cerrado;
      }
    });

    this.publicacionService.getPublicacionesSinValidar().subscribe({
      next: (data) => {
        data.forEach(p => {
          let publicacion: Publicacion64 = {
            id: p.id,
            descripcion: p.descripcion,
            validada: p.validada,
            foto: p.fotoBase64,
            concursante: p.concursante
          };
          this.publicaciones.push(publicacion);
        });
      },
      error: (err) => {
        console.error('Error al obtener las publicaciones:', err);
      }
    });
  }

  validarFoto(validacion: boolean, publicacion: Publicacion64) {
    if (this.sorteoCerrado) {
      alert('El sorteo está cerrado. Ya no se pueden validar fotos.');
      return;
    }
    let publicacionActualizada: Publicacion64 = {
      ...publicacion,
      validada: validacion
    };
    this.publicacionService.updatePublicacion(publicacionActualizada).subscribe({
      next: (data) => {
        this.publicaciones = this.publicaciones.filter(p => p.id !== publicacion.id);
      },
      error: (err) => {
        console.error('Error al actualizar la publicación:', err);
      }
    });
  }
}
