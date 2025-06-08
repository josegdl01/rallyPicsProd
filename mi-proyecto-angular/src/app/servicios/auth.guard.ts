import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { CredencialesService } from './credenciales.service';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
  constructor(private credencialesService: CredencialesService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const token = this.credencialesService.getToken();
    if (!token) {
      this.router.navigate(['/login']);
      return false;
    }
    const rol = this.credencialesService.decodeToken(token).rol;
    const rolesPermitidos = route.data['roles'] as string[];
    if (rolesPermitidos && !rolesPermitidos.includes(rol)) {
      if (rol === 'admin') this.router.navigate(['/admin']);
      else if (rol === 'juez') this.router.navigate(['/juez/' + this.credencialesService.getDecodedId()]);
      else this.router.navigate(['/concursante/' + this.credencialesService.getDecodedId()]);
      return false;
    }
    return true;
  }
}
