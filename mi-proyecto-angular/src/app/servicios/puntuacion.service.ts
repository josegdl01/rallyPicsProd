import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { CredencialesService } from './credenciales.service';
import { Puntuacion } from '../modelos/puntuacion';

@Injectable({
  providedIn: 'root'
})
export class PuntuacionService {

  private URL = 'http://localhost:8084/puntuacion';

  constructor(private http: HttpClient, private credService: CredencialesService) {}

  getPuntuacionesByJuez(juezId: number) {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.credService.getToken()}`
    });
    return this.http.get<Puntuacion[]>(`${this.URL}/getByJuez/${juezId}`, { headers });
  }

  getPuntuacionesByPublicacion(publicacionId: number) {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.credService.getToken()}`
    });
    return this.http.get<Puntuacion[]>(`${this.URL}/getByPublicacion/${publicacionId}`, { headers });
  }

  insertarPuntuacion(puntuacion: Puntuacion) {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.credService.getToken()}`
    });
    return this.http.post<Puntuacion>(`${this.URL}/insert`, puntuacion, { headers });
  }

  getRanking() {
    let peticion = `${this.URL}/rankingConcursantes`;
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.credService.getToken()}`
    });
    console.log('getRanking envía', );
    return this.http.get<any[]>(peticion, {headers});
  }
}
