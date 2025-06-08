import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { CredencialesService } from './credenciales.service';
import { Usuario } from '../modelos/usuario';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  private URL: string = "http://localhost:8084/usuario";
  private headerToken: HttpHeaders;
  constructor(private http: HttpClient, private credencialesService: CredencialesService) {
      this.headerToken = new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${this.credencialesService.getToken()}`
      });
  }

  getAllUsuarios() {
    let peticion: string = `${this.URL}/allUsuarios`;
    let headers = this.headerToken;
    return this.http.get<Usuario[]>(peticion, {headers});
  }

  getUsuarioById(id: number) {
    let peticion: string = `${this.URL}/getUsuario/` + id;
    let headers = this.headerToken;
    console.log('getUsuarioById envía ', peticion, headers);
    return this.http.get<Usuario>(peticion, {headers});
  }

  getUsuarioByEmail(email: string) {
    let peticion: string = `${this.URL}/getUsuarioEmail/` + email;
    let headers = this.headerToken;
    console.log('getUsuarioByEmail envía ', peticion, headers);
    return this.http.get<Usuario>(peticion, {headers});
  }

  insertUsuario(usuario: Usuario) {
    let peticion: string = `${this.URL}/insertUsuario`;
    let headers = this.headerToken;
    console.log('insertUsuario envía ', peticion, usuario, headers);
    return this.http.post<Usuario>(peticion, usuario, {headers}); 
  }

  updateUsuario(usuario : Usuario) {
    let peticion: string = `${this.URL}/updateUsuario`;
    let headers = this.headerToken;
    console.log('updateUsuario envía ', peticion, usuario, headers);
    return this.http.put<Usuario>(peticion, usuario, {headers});
  }

  comprobarContra(contra: string, id: number) {
  const params = {
    contra: contra,
    id: id
  };
  let headers = this.headerToken;
  return this.http.get<any>('http://localhost:8084/auth/comprobarContra', { params, headers });
}

deleteUsuario(id: number) {
  let peticion: string = `${this.URL}/deleteUsuario/${id}`;
  let headers = this.headerToken;
  return this.http.delete(peticion, {headers});
}
}
