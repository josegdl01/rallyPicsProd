import { Component } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CredencialesService } from '../../servicios/credenciales.service';
import { ActivatedRoute, Router} from '@angular/router';
import { ConcursanteService } from '../../servicios/concursante.service';
import { Concursante } from '../../modelos/concursante';
import { UsuarioService } from '../../servicios/usuario.service';

@Component({
  selector: 'app-concursante-cuenta',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './concursante-cuenta.component.html',
  styleUrls: ['./concursante-cuenta.component.css'],
  providers: [DatePipe]

})
export class ConcursanteCuentaComponent {
  public concursante: Concursante = <Concursante>{};
  concursanteForm: FormGroup;
  contraForm: FormGroup;
  mensajeCampos: string = 'Debes rellenar este campo.';
  editando: boolean = false;
  cambiarContra: boolean = false;

  constructor(private fb: FormBuilder, private datePipe: DatePipe, private concursanteService: ConcursanteService, private credencialesService: CredencialesService, private router: Router, private activatedRoute: ActivatedRoute, private usuarioService: UsuarioService) {
    this.concursanteForm = this.fb.group({
      email: ['', [Validators.required]],
      nombre: ['', Validators.required],
      apellidos: ['', Validators.required],
      fechaNacimiento: ['', [Validators.required]]
    });

    this.contraForm = this.fb.group({
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', Validators.required],
      oldPassword: ['', [Validators.required, Validators.minLength(6)]]
    }, { validators: this.passwordsMatchValidator });

    const id = this.activatedRoute.snapshot.params['id'];
    console.log('parametro: ', id);
    if (id) {
      this.concursanteService.getConcursanteById(id).subscribe({
        next: (concursante) => {
          this.concursante = concursante;
          this.concursanteForm.patchValue({
            email: concursante.usuario.email,
            nombre: concursante.usuario.nombre,
            apellidos: concursante.usuario.apellidos,
            fechaNacimiento: concursante.usuario.fechaNacimiento
          });
        },
        error: (err) => {
          console.error('Error al obtener el usuario:', err);
        }
      });
    }

  }
  passwordsMatchValidator(form: FormGroup) {
    return form.get('password')!.value === form.get('confirmPassword')!.value
      ? null : { mismatch: true };

  }

  get email() { return this.concursanteForm.get('email'); }
  get password() { return this.concursanteForm.get('password'); }
  get nombre() { return this.concursanteForm.get('nombre'); }
  get apellidos() { return this.concursanteForm.get('apellidos'); }
  get fechaNacimiento() { return this.concursanteForm.get('fechaNacimiento'); }
  get confirmPassword() { return this.concursanteForm.get('confirmPassword'); }
  get oldPassword() { return this.contraForm.get('oldPassword'); }

  habilitarEdicion() {
    this.editando = !this.editando;
  }

  mostrarContraForm() {
    this.cambiarContra = !this.cambiarContra;
    if (this.cambiarContra) {
      this.contraForm.reset();
    }
  }

  borrarCuenta() {
    if (confirm('¿Estás seguro de que quieres borrar tu cuenta? Esta acción no se puede deshacer.')) {
      this.concursanteService.deleteConcursante(this.concursante.id).subscribe({
        next: (data) => {
          console.log(data);
          alert('Cuenta borrada correctamente.');
          this.credencialesService.clearToken();
          this.router.navigate(['/']);
        },
        error: (err) => {
          console.error('Error al borrar la cuenta:', err);
          alert('Error al borrar la cuenta. Inténtalo de nuevo más tarde.');
        }
      });

    }
  }

  onSubmit() {
    if (this.concursanteForm.valid) {
      const formValue = {
        ...this.concursanteForm.value,
        fechaNacimiento: this.datePipe.transform(this.concursanteForm.value.fechaNacimiento, 'yyyy-MM-dd')
      };
      console.log('Datos del formulario:', formValue);
      let concursante: Concursante = {
        id: this.concursante.id,
        fotos: this.concursante.fotos,
        usuario: {
          id: this.concursante.usuario.id,
          nombre: formValue.nombre,
          apellidos: formValue.apellidos,
          email: formValue.email,
          password: this.concursante.usuario.password,
          fechaNacimiento: formValue.fechaNacimiento,
          esJuez: false
        }
      }

      this.concursanteService.updateConcursante(concursante).subscribe({
        next: (value) => {
          console.log("El concursante se ha actualizado correctamente", value);
          this.editando = false;
          alert('Datos actualizados correctamente.');
        },
      });
    }
  }

  onSubmitContra() {
    if (this.contraForm.valid) {
      this.usuarioService.comprobarContra(this.contraForm.value.oldPassword, this.concursante.usuario.id).subscribe({
        next: (value) => {
          let res = value;
          console.log('akitalavaina', res);
          let switchRes = res.status == 'OK' ? 'A' : res == 'NOTOK' ? 'B' : 'C'

          console.log('Resultado de la comprobación de contraseña:', switchRes);

          switch (switchRes) {
            case 'A': {
              if (this.contraForm.hasError('mismatch')) {
                alert('Las contraseñas no coinciden.');
                return;
              }
              const formValue = {
                ...this.contraForm.value
              };
              console.log('Datos del formulario:', formValue);
              let concursante: Concursante = {
                ...this.concursante,
                usuario: {
                  ...this.concursante.usuario,
                  password: formValue.password
                }
              };

              this.concursanteService.updateConcursante(concursante).subscribe({
                next: (value) => {
                  console.log("El concursante se ha actualizado correctamente", value);
                  this.editando = false;
                  alert('Datos actualizados correctamente.');
                },
              });
              break;
            }

            case 'B': {
              alert('La contraseña antigua no es correcta. Por favor, inténtalo de nuevo.');
              break;
            }

            case 'C': {
              alert('Ha ocurrido un error al comprobar la contraseña. Por favor, inténtalo de nuevo más tarde.');
              console.error('Error al comprobar la contraseña');
              break;
            }
          }
        },
      });
    }
  }
}
