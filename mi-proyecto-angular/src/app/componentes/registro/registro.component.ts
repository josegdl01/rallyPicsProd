import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { CommonModule } from '@angular/common';
import { UsuarioService } from '../../servicios/usuario.service';
import { CredencialesService } from '../../servicios/credenciales.service';
import { Concursante } from '../../modelos/concursante';
import { Juez } from '../../modelos/juez';
import { Router } from '@angular/router';

@Component({
  selector: 'app-registro',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './registro.component.html',
  styleUrls: ['./registro.component.css'],
  providers: [DatePipe]
})
export class RegistroComponent {
  registroForm: FormGroup;
  mensajeCampos: string = 'Debes rellenar este campo.';

  constructor(private fb: FormBuilder, private datePipe: DatePipe, private usuarioService: UsuarioService, private credencialesService: CredencialesService, private router: Router) {
    this.credencialesService.clearToken();
    this.registroForm = this.fb.group({
      email: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      nombre: ['', Validators.required],
      apellidos: ['', Validators.required],
      fechaNacimiento: ['', [Validators.required]],
      confirmPassword: ['', Validators.required],
      tipoUsuario: ['', Validators.required]

    });
  }

  get email() { return this.registroForm.get('email'); }
  get password() { return this.registroForm.get('password'); }
  get nombre() { return this.registroForm.get('nombre'); }
  get apellidos() { return this.registroForm.get('apellidos'); }
  get fechaNacimiento() { return this.registroForm.get('fechaNacimiento'); }
  get confirmPassword() { return this.registroForm.get('confirmPassword'); }
  get tipoUsuario() { return this.registroForm.get('tipoUsuario'); }

  onSubmit(): void {
    if (this.registroForm.valid) {
      const formValue = {
        ...this.registroForm.value,
        fechaNacimiento: this.datePipe.transform(this.registroForm.value.fechaNacimiento, 'yyyy-MM-dd')
      };
      console.log('Datos del formulario:', formValue);
      if (this.registroForm.value.tipoUsuario == "concursante") {
        let concursante: Concursante = {
          id: -1,
          fotos: [],
          usuario: {
            id: -1,
            nombre: formValue.nombre,
            apellidos: formValue.apellidos,
            email: formValue.email,
            password: formValue.password,
            fechaNacimiento: formValue.fechaNacimiento,
            esJuez: false
          }
        }

        this.credencialesService.registraConcursante(concursante).subscribe({
          next: (data) => {
            console.log("El concursante se ha registrado correctamente", data);
          }
        })
      } else {
        let juez: Juez = {
          id: -1,
          puedeValidar: false,
          usuario: {
            id: -1,
            nombre: formValue.nombre,
            apellidos: formValue.apellidos,
            email: formValue.email,
            password: formValue.password,
            fechaNacimiento: formValue.fechaNacimiento,
            esJuez: true
          }
        }

        this.credencialesService.registrarJuez(juez).subscribe({
          next: (data) => {
            console.log("El juez se ha registrado correctamente", data);
          }
        })
      }
      this.router.navigate(['/login']);
    }
  }
}