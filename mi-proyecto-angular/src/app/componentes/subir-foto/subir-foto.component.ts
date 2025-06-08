import { Component, ViewChild, ElementRef, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { PublicacionService } from '../../servicios/publicacion.service';
import { CommonModule } from '@angular/common';
import { Concursante } from '../../modelos/concursante';
import { ActivatedRoute } from '@angular/router';
import { ConcursanteService } from '../../servicios/concursante.service';
import { Publicacion } from '../../modelos/publicacion';
import { ConfiguracionService } from '../../servicios/configuracion.service';

@Component({
  selector: 'app-subir-foto',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './subir-foto.component.html',
  styleUrls: ['./subir-foto.component.css']
})
export class SubirFotoComponent implements OnInit {
  publicacionForm: FormGroup;
  fotoSeleccionada: File | null = null;
  previewUrl: string | ArrayBuffer | null = null;
  errorMessage: string = '';
  concursante : Concursante = <Concursante>{};
  sorteoCerrado = false;

  @ViewChild('fileInput') fileInput!: ElementRef<HTMLInputElement>;

  constructor(
    private fb: FormBuilder, 
    private publicacionService: PublicacionService, 
    private activatedRoute: ActivatedRoute, 
    private concursanteService: ConcursanteService,
    private configuracionService: ConfiguracionService
  ) {
    this.concursanteService.getConcursanteById(this.activatedRoute.snapshot.params['id']).subscribe({
      next: (data) => {
        console.log('Concursante obtenido:', data);
        this.concursante = data;
      },
      error: (err) => {
        console.error('Error al obtener el concursante:', err);
        this.errorMessage = 'No se pudo obtener el concursante. Inténtalo más tarde.';
      }
    });

    this.publicacionForm = this.fb.group({
      descripcion: ['', [Validators.required, Validators.maxLength(255)]]
    });
  }

  ngOnInit() {
    this.configuracionService.getEstadoSorteo().subscribe({
      next: (res) => {
        this.sorteoCerrado = res.cerrado;
      }
    });
  }

  onFileChange(event: Event) {
    const input = event.target as HTMLInputElement;
    this.errorMessage = '';
    
    if (input.files && input.files[0]) {
      const file = input.files[0];
      
      if (!['image/jpeg', 'image/png'].includes(file.type)) {
        this.errorMessage = 'Solo se permiten archivos JPG o PNG';
        return;
      }
      
      if (file.size > 10 * 1024 * 1024) { // 10MB
        this.errorMessage = 'La imagen excede el tamaño máximo de 10MB';
        return;
      }

      const reader = new FileReader();
      reader.onload = (e) => this.previewUrl = e.target?.result!;
      reader.readAsDataURL(file);
      
      this.fotoSeleccionada = file;
    }

    console.log('formulario', FormData);
  }

  get fotoValida(): boolean {
    return !!this.fotoSeleccionada && !this.errorMessage;
  }

  limpiarForm(){
    this.publicacionForm.reset();
    this.fotoSeleccionada = null;
    this.previewUrl = null;
    this.errorMessage = '';
    if (this.fileInput) {
      this.fileInput.nativeElement.value = '';
    }
  }

  onSubmit() {
    if (this.sorteoCerrado) {
      alert('El sorteo está cerrado. Ya no se pueden subir fotos.');
      return;
    }

    if (this.publicacionForm.valid && this.fotoValida) {
      console.log(this.publicacionForm.value);

      let publicacion: Publicacion = {
        id: -1,
        descripcion: this.publicacionForm.value.descripcion,
        concursante: this.concursante,
        validada: false,
        foto: this.fotoSeleccionada!
      };

      console.log('Publicación a enviar:', publicacion);

      this.publicacionService.insertPublicacion(publicacion).subscribe({
        next: (res) => {
          console.log('resultado', res);
          this.limpiarForm();
        },
        error: (err) => {
          if (err?.status === 403) {
            this.errorMessage = 'La imagen que has intentado subir es demasiado grande.';
          } else {
            this.errorMessage = err.error?.message || 'Error al subir la publicación';
          }
        }
      });
    }
  }
}