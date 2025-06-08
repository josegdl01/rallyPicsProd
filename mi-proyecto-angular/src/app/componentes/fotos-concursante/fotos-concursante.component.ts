import { Component } from '@angular/core';
import { Concursante } from '../../modelos/concursante';
import { ActivatedRoute, RouterLink, RouterModule } from '@angular/router';
import { PublicacionService } from '../../servicios/publicacion.service';
import { CommonModule } from '@angular/common';
import { Publicacion64 } from '../../modelos/publicacion64';
import { ConcursanteService } from '../../servicios/concursante.service';

@Component({
  selector: 'app-fotos-concursante',
  imports: [CommonModule, RouterModule, RouterLink],
  templateUrl: './fotos-concursante.component.html',
  styleUrls: ['./fotos-concursante.component.css']
})
export class FotosConcursanteComponent {

  public concursante: Concursante = <Concursante>{};
  public publicaciones: Publicacion64[] = [];

  constructor(private publicacionService: PublicacionService, private activatedRoute: ActivatedRoute) { 
    this.publicacionService.getPublicacionesByConc(this.activatedRoute.snapshot.params['id']).subscribe({
      next: (data) => {
        console.log('Publicaciones obtenidas:', data);
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
}
