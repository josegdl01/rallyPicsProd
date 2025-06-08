import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CredencialesService } from './credenciales.service';
import { Concursante } from '../modelos/concursante';

@Injectable({
  providedIn: 'root'
})
export class ConcursanteService {

  private URL: string = "http://localhost:8084/concursante";
  private headerToken: HttpHeaders;

  constructor(private http: HttpClient, private credService: CredencialesService) {
    this.headerToken = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.credService.getToken()}`
    });
  }

  getConcursanteById(id: number){
    let peticion = `${this.URL}/getConc?id=${id}`;
    let headers = this.headerToken;
    console.log("getConcursanteById envía", peticion, this.headerToken);
    return this.http.get<Concursante>(peticion, {headers});
  }

  deleteConcursante(id: number){
    let peticion = `${this.URL}/deleteConcursante/` + id;
    let headers = this.headerToken;
    console.log("deleteConcursante envía", peticion, this.headerToken);
    return this.http.delete<any>(peticion, {headers});
  }

  updateConcursante(concursante: Concursante) {
    let peticion = `${this.URL}/updateConcursante`;
    let headers = this.headerToken;
    console.log("updateConcursante envía", peticion, this.headerToken);
    return this.http.put<Concursante>(peticion, concursante, { headers });
  }

  getIdByUsuarioId(usuarioId: number) {
    let peticion = `${this.URL}/getIdByUsuario/` + usuarioId;
    let headers = this.headerToken;
    console.log("getIdByUsuarioId envía", peticion, this.headerToken);
    return this.http.get<number>(peticion, {headers});
  }
}
