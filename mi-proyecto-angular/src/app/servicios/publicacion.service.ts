import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CredencialesService } from './credenciales.service';
import { Publicacion } from '../modelos/publicacion';
import { Publicacion64 } from '../modelos/publicacion64';

@Injectable({
  providedIn: 'root'
})
export class PublicacionService {

  private URL: string = "http://localhost:8084/publicacion";
  private headerToken: HttpHeaders;

  constructor(private http: HttpClient, private credService: CredencialesService) {
    this.headerToken = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.credService.getToken()}`
    });
  }

  insertPublicacion(publicacion: Publicacion) {
    let peticion = `${this.URL}/subirPublicacion`;

    const formData = new FormData();
    formData.append('descripcion', publicacion.descripcion);
    formData.append('validada', String(publicacion.validada));
    formData.append('foto', publicacion.foto);

    formData.append('concursanteId', String(publicacion.concursante.id));

    let headers = new HttpHeaders({
      'Authorization': `Bearer ${this.credService.getToken()}`
    });

    return this.http.post<Publicacion>(peticion, formData, { headers });
  }

  getPublicacionesByConc(id: number) {
    let peticion = `${this.URL}/getPublicacionesByConc/${id}`;
    let headers = this.headerToken;
    return this.http.get<any[]>(peticion, {headers});
  }

  getPublicacionesSinValidar(){
    let peticion = `${this.URL}/getPublicacionesSinValidar`;
    let headers = this.headerToken;
    return this.http.get<any[]>(peticion, {headers});
  }

  getPublicacionesValidadas() {
    let peticion = `${this.URL}/getPublicacionesValidadas`;
    let headers = this.headerToken;
    return this.http.get<any[]>(peticion, {headers});
  }

  updatePublicacion(publicacion: Publicacion64) {
  const peticion = `${this.URL}/updatePublicacion/${publicacion.id}`;
  const headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${this.credService.getToken()}`
  });
  return this.http.put<Publicacion64>(peticion, publicacion, { headers });
}

getAllPublicaciones() {
  const peticion = `${this.URL}/getAllPublicaciones`;
  const headers = this.headerToken;
  return this.http.get<Publicacion64[]>(peticion, {headers});
}

deletePublicacion(id: number) {
  const peticion = `${this.URL}/deletePublicacion/${id}`;
  const headers = this.headerToken;
  return this.http.delete(peticion, {headers});
}
}
