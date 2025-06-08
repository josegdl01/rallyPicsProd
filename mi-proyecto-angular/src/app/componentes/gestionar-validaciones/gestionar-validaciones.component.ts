import { Component } from '@angular/core';
import { JuezService } from '../../servicios/juez.service';
import { Juez } from '../../modelos/juez';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-gestionar-validaciones',
  standalone: true,
  templateUrl: './gestionar-validaciones.component.html',
  styleUrl: './gestionar-validaciones.component.css',
  imports: [CommonModule]
})
export class GestionarValidacionesComponent {
  jueces: Juez[] = [];

  constructor(private juezService: JuezService) {
    this.cargarJueces();
  }

  cargarJueces() {
    this.juezService.getAllJueces().subscribe({
      next: (data) => this.jueces = data,
      error: () => this.jueces = []
    });
  }

  togglePuedeValidar(juez: Juez) {
    const juezActualizado = { ...juez, puedeValidar: !juez.puedeValidar };
    this.juezService.updateJuez(juezActualizado).subscribe({
      next: () => {
        juez.puedeValidar = !juez.puedeValidar;
      },
      error: () => {
        alert('No se pudo actualizar el permiso de validación.');
      }
    });
  }
}
