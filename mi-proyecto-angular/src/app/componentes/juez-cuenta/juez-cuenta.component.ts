import { Component } from '@angular/core';
import { Juez } from '../../modelos/juez';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule, DatePipe } from '@angular/common';
import { JuezService } from '../../servicios/juez.service';
import { CredencialesService } from '../../servicios/credenciales.service';
import { ActivatedRoute, Router} from '@angular/router';
import { UsuarioService } from '../../servicios/usuario.service';

@Component({
  selector: 'app-juez-cuenta',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './juez-cuenta.component.html',
  styleUrl: './juez-cuenta.component.css',
   providers: [DatePipe]
})
export class JuezCuentaComponent {

  public juez: Juez = <Juez>{};
  juezForm: FormGroup;
  contraForm: FormGroup;
  mensajeCampos: string = 'Debes rellenar este campo.';
  editando: boolean = false;
  cambiarContra: boolean = false;

  constructor(private fb: FormBuilder, private datePipe: DatePipe, private juezService: JuezService, private credencialesService: CredencialesService, private router: Router, private activatedRoute: ActivatedRoute, private usuarioService: UsuarioService) {
    this.juezForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
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
    if (id) {
      this.juezService.getJuezById(id).subscribe({
        next: (juez) => {
          this.juez = juez;
          this.juezForm.patchValue({
            email: juez.usuario.email,
            nombre: juez.usuario.nombre,
            apellidos: juez.usuario.apellidos,
            fechaNacimiento: juez.usuario.fechaNacimiento
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

  get email() { return this.juezForm.get('email'); }
  get password() { return this.juezForm.get('password'); }
  get nombre() { return this.juezForm.get('nombre'); }
  get apellidos() { return this.juezForm.get('apellidos'); }
  get fechaNacimiento() { return this.juezForm.get('fechaNacimiento'); }
  get confirmPassword() { return this.juezForm.get('confirmPassword'); }
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
    if (confirm('¿Estás seguro de que quieres darte de baja? Esta acción no se puede deshacer.')) {
      this.juezService.deleteJuez(this.activatedRoute.snapshot.params['id']).subscribe({
        next: () => {
          alert('Cuenta eliminada correctamente.');
          this.router.navigate(['/login']);
        },
        error: () => {
          alert('Error al eliminar la cuenta.');
        }
      });
    }
  }

  onSubmit() {
    if (this.juezForm.valid) {
      const formValue = {
        ...this.juezForm.value,
        fechaNacimiento: this.datePipe.transform(this.juezForm.value.fechaNacimiento, 'yyyy-MM-dd')
      };
      console.log('Datos del formulario:', formValue);
      let juez: Juez = {
        id: this.juez.id,
        puedeValidar: this.juez.puedeValidar,
        usuario: {
          id: this.juez.usuario.id,
          nombre: formValue.nombre,
          apellidos: formValue.apellidos,
          email: formValue.email,
          password: this.juez.usuario.password,
          fechaNacimiento: formValue.fechaNacimiento,
          esJuez: this.juez.usuario.esJuez
        }
      }

      this.juezService.updateJuez(juez).subscribe({
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
      this.usuarioService.comprobarContra(this.contraForm.value.oldPassword, this.juez.usuario.id).subscribe({
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
              let juez: Juez = {
                ...this.juez,
                usuario: {
                  ...this.juez.usuario,
                  password: formValue.password
                }
              };

              this.juezService.updateJuez(juez).subscribe({
                next: (value) => {
                  console.log("El juez se ha actualizado correctamente", value);
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
