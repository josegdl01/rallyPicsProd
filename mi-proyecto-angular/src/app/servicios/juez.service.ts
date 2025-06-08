import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CredencialesService } from './credenciales.service';
import { Juez } from '../modelos/juez';

@Injectable({
  providedIn: 'root'
})
export class JuezService {

  private URL: string = "http://localhost:8084/juez";
  private headerToken: HttpHeaders;
  constructor(private http: HttpClient, private credService: CredencialesService) {
    this.headerToken = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.credService.getToken()}`
    });
  }

  getJuezById(id: number){
    let peticion = `${this.URL}/getJuez?id=${id}`;
    let headers = this.headerToken;
    console.log("getJuezById envía", peticion, this.headerToken);
    return this.http.get<Juez>(peticion, {headers});
  }

  deleteJuez(id: number) {
    let peticion = `${this.URL}/deleteJuez/${id}`;
    let headers = this.headerToken;
    console.log("deleteJuez envía", peticion, this.headerToken);
    return this.http.delete(peticion, {headers});
  }

  updateJuez(juez: Juez) {
    let peticion = `${this.URL}/updateJuez`;
    let headers = this.headerToken;
    console.log("updateJuez envía", peticion, juez, this.headerToken);
    return this.http.put<Juez>(peticion, juez, {headers});
  }

  getIdByUsuarioId(usuarioId: number) {
    let peticion = `${this.URL}/getIdByUsuario/` + usuarioId;
    let headers = this.headerToken;
    console.log("getIdByUsuarioId envía", peticion, this.headerToken);
    return this.http.get<number>(peticion, {headers});
  }

  getAllJueces() {
    let peticion = `${this.URL}/getAllJueces`;
    let headers = this.headerToken;
    console.log('getAllJueces envía', peticion, headers);
    return this.http.get<Juez[]>(peticion, { headers });
  }
}
