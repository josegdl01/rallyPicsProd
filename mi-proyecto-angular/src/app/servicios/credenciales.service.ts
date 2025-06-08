import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Jwt } from '../modelos/jwt';
import { Credenciales } from '../modelos/credenciales';
import { Concursante } from '../modelos/concursante';
import { Juez } from '../modelos/juez';

@Injectable({
  providedIn: 'root'
})
export class CredencialesService {

  private URL: string = "http://localhost:8084/auth";

  constructor(private http: HttpClient) { }

  enviarLogin(creds : Credenciales){
    let peticion = `${this.URL}/login`;
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    console.log("EL LOGIN ENVÍA", peticion, creds, headers);

    return this.http.post<Jwt>(peticion, creds, {headers});
  }

  registraConcursante(concursante : Concursante){
    let peticion = `${this.URL}/registroConc`;
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    console.log("EL REGISTRO ENVÍA", peticion, concursante, {headers});

    return this.http.post<Concursante>(peticion, concursante, {headers});
  }

  registrarJuez(juez : Juez){
    let peticion = `${this.URL}/registroJuez`;
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    console.log("EL REGISTRO ENVÍA", peticion, juez, headers);

    return this.http.post<Juez>(peticion, juez, {headers});
  }

  saveToken(jwt : string){
    sessionStorage.setItem("token", jwt);
  }

  getToken() {
  if (typeof window !== 'undefined' && window.sessionStorage) {
    let token = sessionStorage.getItem("token") != null ? sessionStorage.getItem("token") : "";
    return token;
  }
  return "";
}

  decodeToken(token : string){
    const util = token.split(".")[1];

    return JSON.parse(atob(util));
  }

  getDecodedRol(){
    let token = this.getToken();
    let rol = "";

    if(token != "" && token != null){
        rol = this.decodeToken(token).sub;
    }
    console.log("getDecodedRol", rol);
    return rol;
  }

  getDecodedId(){
    let token = this.getToken();
    let idUser = 0;

    if(token != "" && token != null){
        idUser = this.decodeToken(token).sub;
    }

    return idUser;
  }

  clearToken() {
    if (sessionStorage.getItem("token")) {
      sessionStorage.removeItem("token");
    }
  }
}
