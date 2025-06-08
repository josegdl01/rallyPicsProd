import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CredencialesService } from './credenciales.service';

@Injectable({
  providedIn: 'root'
})
export class ConfiguracionService {

  private URL: string = "http://localhost:8084/configuracion";
  private headerToken: HttpHeaders;

  constructor(private http: HttpClient, private credService: CredencialesService) {
    this.headerToken = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.credService.getToken()}`
    });
  }

  cerrarSorteo() {
    let peticion = `${this.URL}/cerrarSorteo`;
    let headers = this.headerToken;
    return this.http.post<any>(peticion, {}, {headers});
  }

  getEstadoSorteo() {
    let peticion = `${this.URL}/estadoSorteo`;
    let headers = this.headerToken;
    return this.http.get<any>(peticion, {headers}) ;
  }
}
