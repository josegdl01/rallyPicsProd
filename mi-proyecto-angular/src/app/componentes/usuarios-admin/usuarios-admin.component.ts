import { Component } from '@angular/core';
import { UsuarioService } from '../../servicios/usuario.service';
import { JuezService } from '../../servicios/juez.service';
import { ConcursanteService } from '../../servicios/concursante.service';
import { Usuario } from '../../modelos/usuario';
import { Juez } from '../../modelos/juez';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-usuarios-admin',
  templateUrl: './usuarios-admin.component.html',
  styleUrl: './usuarios-admin.component.css',
  imports: [CommonModule, RouterLink]
})
export class UsuariosAdminComponent {
  usuarios: Usuario[] = [];

  constructor(private usuarioService: UsuarioService, private juezService: JuezService, private concursanteService: ConcursanteService) {
    this.cargarUsuarios();
  }

  cargarUsuarios() {
    this.usuarioService.getAllUsuarios().subscribe({
      next: (data) => {
        this.usuarios = data;
        this.usuarios.forEach(usuario => {
          if (usuario.esJuez) {
            this.juezService.getIdByUsuarioId(usuario.id).subscribe({
              next: (idJuez) => {
                this.juezService.getJuezById(idJuez).subscribe({
                  next: (juez: Juez) => {
                    (usuario as any).puedeValidar = juez.puedeValidar;
                  }
                });
              }
            });
          }
        });
      },
      error: (err) => {
        console.error('Error al obtener los usuarios:', err);
      }
    });
  }

  borrarUsuario(usuario: Usuario) {
    console.log('skere', usuario);
    if (confirm('¿Seguro que quieres borrar este usuario?')) {
      if (usuario.esJuez) {
        this.juezService.getIdByUsuarioId(usuario.id).subscribe({
          next: (idJuez) => {
            this.juezService.deleteJuez(idJuez).subscribe({
              next: () => {
                this.cargarUsuarios();
              },
              error: (err) => {
                console.error('Error al borrar el juez:', err);
              }
            });
          },
          error: (err) => {
            console.error('Error al obtener el ID del juez:', err);
          }
        });
      } else {
        this.concursanteService.getIdByUsuarioId(usuario.id).subscribe({
          next: (idConcursante) => {
            this.concursanteService.deleteConcursante(idConcursante).subscribe({
              next: () => {
                this.cargarUsuarios();
              },
              error: (err) => {
                console.error('Error al borrar el concursante:', err);
              }
            });
          },
          error: (err) => {
            console.error('Error al obtener el ID del concursante:', err);
          }
        });
      }
    }
  }

  cambiarPuedeValidar(usuario: Usuario, nuevoValor: boolean) {
    if (!usuario.esJuez) return;

    this.juezService.getIdByUsuarioId(usuario.id).subscribe({
      next: (idJuez) => {
        this.juezService.getJuezById(idJuez).subscribe({
          next: (juez: Juez) => {
            const juezActualizado: Juez = { ...juez, puedeValidar: nuevoValor };
            this.juezService.updateJuez(juezActualizado).subscribe({
              next: () => {
                (usuario as any).puedeValidar = nuevoValor;
              },
              error: () => {
                alert('No se pudo actualizar el permiso de validación.');
              }
            });
          }
        });
      }
    });
  }
}
