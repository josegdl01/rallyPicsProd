import { Component } from '@angular/core';
import { PublicacionService } from '../../servicios/publicacion.service';
import { Publicacion64 } from '../../modelos/publicacion64';
import { CommonModule } from '@angular/common';
import { RouterModule, RouterLink } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-fotos-admin',
  imports: [CommonModule, RouterModule, RouterLink],
  templateUrl: './fotos-admin.component.html',
  styleUrl: './fotos-admin.component.css'
})
export class FotosAdminComponent {
  publicaciones: Publicacion64[] = [];

  constructor(private publicacionService: PublicacionService) {
    this.cargarPublicaciones();
  }

  cargarPublicaciones() {
    this.publicacionService.getAllPublicaciones().subscribe({
      next: (data) => {
        this.publicaciones = data.map((p: any) => ({
          ...p,
          foto: p.fotoBase64
        }));
      },
      error: (err) => {
        console.error('Error al obtener las publicaciones:', err);
      }
    });
  }

  borrarFoto(id: number) {
    if (confirm('¿Seguro que quieres borrar esta foto?')) {
      this.publicacionService.deletePublicacion(id).subscribe({
        next: () => {
          this.cargarPublicaciones();
        },
        error: (err) => {
          this.cargarPublicaciones();
          setTimeout(() => {
            const sigue = this.publicaciones.some(p => p.id === id);
            if (sigue) {
              alert('Error al borrar la foto');
            }
          }, 500);
        }
      });
    }
  }
}
