import { Component, OnInit, AfterViewInit, ElementRef, Renderer2 } from '@angular/core';
import { PublicacionService } from '../../servicios/publicacion.service';
import { CommonModule } from '@angular/common';

declare var bootstrap: any;

@Component({
  standalone: true,
  imports: [CommonModule],
  selector: 'app-galeria-publica',
  templateUrl: './galeria.component.html',
  styleUrls: ['./galeria.component.css']
})
export class GaleriaPublicaComponent implements OnInit, AfterViewInit {
  publicaciones: any[] = [];

  constructor(
    private publicacionService: PublicacionService,
    private el: ElementRef,
    private renderer: Renderer2
  ) {}

  ngOnInit() {
    this.publicacionService.getPublicacionesValidadas().subscribe({
      next: (data) => {
        this.publicaciones = data;
        setTimeout(() => this.inicializarCarousel(), 0);
      },
      error: () => {
        this.publicaciones = [];
      }
    });
  }

  ngAfterViewInit() {
    this.inicializarCarousel();
  }

  inicializarCarousel() {
    const carouselElement = this.el.nativeElement.querySelector('#galeriaCarousel');
    if (carouselElement && typeof bootstrap !== 'undefined') {
      try {
        bootstrap.Carousel.getOrCreateInstance(carouselElement);
      } catch (e) {
      }
    }
  }
}
