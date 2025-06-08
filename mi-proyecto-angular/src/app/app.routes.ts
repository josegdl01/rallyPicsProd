import { Routes } from '@angular/router';
import { RegistroComponent } from './componentes/registro/registro.component';
import { LoginComponent } from './componentes/login/login.component';
import { HomeComponent } from './componentes/home/home.component';
import { MainConcursanteComponent } from './componentes/main-concursante/main-concursante.component';
import { MainJuezComponent } from './componentes/main-juez/main-juez.component';
import { SubirFotoComponent } from './componentes/subir-foto/subir-foto.component';
import { ConcursanteCuentaComponent } from './componentes/concursante-cuenta/concursante-cuenta.component';
import { FotosConcursanteComponent } from './componentes/fotos-concursante/fotos-concursante.component';
import { FotosJuezComponent } from './componentes/fotos-juez/fotos-juez.component';
import { JuezCuentaComponent } from './componentes/juez-cuenta/juez-cuenta.component';
import { PuntuarFotosComponent } from './componentes/puntuar-fotos/puntuar-fotos.component';
import { ListadoPuntuacionesComponent } from './componentes/listado-puntuaciones/listado-puntuaciones.component';
import { MainAdminComponent } from './componentes/main-admin/main-admin.component';
import { UsuariosAdminComponent } from './componentes/usuarios-admin/usuarios-admin.component';
import { FotosAdminComponent } from './componentes/fotos-admin/fotos-admin.component';
import { AuthGuard } from './servicios/auth.guard';
import { GestionarValidacionesComponent } from './componentes/gestionar-validaciones/gestionar-validaciones.component';
import { EstadisticasComponent } from './componentes/estadisticas/estadisticas.component';

export const routes: Routes = [
    { path: 'registro', component: RegistroComponent },
    { path: 'login', component: LoginComponent },
    { path: '', component: HomeComponent },

    {
        path: 'concursante/:id',
        component: MainConcursanteComponent,
        canActivate: [AuthGuard],
        data: { roles: ['conc'] }
    },
    {
        path: 'subirFoto/:id',
        component: SubirFotoComponent,
        canActivate: [AuthGuard],
        data: { roles: ['conc'] }
    },
    {
        path: 'concCuenta/:id',
        component: ConcursanteCuentaComponent,
        canActivate: [AuthGuard],
        data: { roles: ['conc'] }
    },
    {
        path: 'fotosConc/:id',
        component: FotosConcursanteComponent,
        canActivate: [AuthGuard],
        data: { roles: ['conc'] }
    },

    {
        path: 'juez/:id',
        component: MainJuezComponent,
        canActivate: [AuthGuard],
        data: { roles: ['juez'] }
    },
    {
        path: 'fotosJuez/:id',
        component: FotosJuezComponent,
        canActivate: [AuthGuard],
        data: { roles: ['juez'] }
    },
    {
        path: 'puntuarFotos/:id',
        component: PuntuarFotosComponent,
        canActivate: [AuthGuard],
        data: { roles: ['juez'] }
    },
    {
        path: 'juezCuenta/:id',
        component: JuezCuentaComponent,
        canActivate: [AuthGuard],
        data: { roles: ['juez'] }
    },

    {
        path: 'puntuacionPublicacion/:id',
        component: ListadoPuntuacionesComponent,
        canActivate: [AuthGuard],
        data: { roles: ['admin', 'juez', 'conc'] }
    },

    {
        path: 'admin',
        component: MainAdminComponent,
        canActivate: [AuthGuard],
        data: { roles: ['admin'] }
    },
    {
        path: 'adminUsuarios',
        component: UsuariosAdminComponent,
        canActivate: [AuthGuard],
        data: { roles: ['admin'] }
    },
    {
        path: 'adminFotos',
        component: FotosAdminComponent,
        canActivate: [AuthGuard],
        data: { roles: ['admin'] }
    },
    {
        path: 'gestionarValidaciones',
        component: GestionarValidacionesComponent,
        canActivate: [AuthGuard],
        data: { roles: ['admin'] }
    },
    {
        path: 'estadisticas',
        component: EstadisticasComponent,
        canActivate: [AuthGuard],
        data: { roles: ['admin'] }
    },

    { path: '', component: HomeComponent }
];
