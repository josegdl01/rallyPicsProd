import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { CredencialesService } from '../../servicios/credenciales.service';
import { Credenciales } from '../../modelos/credenciales';
import { Router } from '@angular/router';
import { RouterLink } from '@angular/router';
@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, CommonModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  registroForm: FormGroup;
  mensajeCampos: string = 'Debes rellenar este campo.';
  mensajeError: string = ''; 
  constructor(private fb: FormBuilder, private credencialesService: CredencialesService, private router: Router) {
    this.credencialesService.clearToken();
    this.registroForm = this.fb.group({
      email: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  get email() { return this.registroForm.get('email'); }
  get password() { return this.registroForm.get('password'); }

  onSubmit(): void {
    if (this.registroForm.valid) {
      const formValue = { ...this.registroForm.value };
      let credenciales: Credenciales = {
        email: formValue.email,
        contrasenya: formValue.password,
        rol: "concursante"
      };
      this.credencialesService.enviarLogin(credenciales).subscribe({
        next: (data) => {
          sessionStorage.setItem('token', data.token);
          console.log("Token decodificado: ", this.credencialesService.decodeToken(data.token));
          let decToken = this.credencialesService.decodeToken(data.token);
          decToken.rol == "conc" ? this.router.navigate(['/concursante/' +decToken.sub]) : decToken.rol == "juez" ? this.router.navigate(['/juez/' + decToken.sub]) : this.router.navigate(['/admin']);
        },
        error: (err) => {
          if (err.status === 404) {
            this.mensajeError = "No existe ningún usuario con ese email.";
          } else if (err.status === 401) {
            this.mensajeError = "La contraseña introducida es incorrecta.";
          } else {
            this.mensajeError = "Error inesperado. Inténtalo de nuevo más tarde.";
          }
        }
      });
    }
  }
}
