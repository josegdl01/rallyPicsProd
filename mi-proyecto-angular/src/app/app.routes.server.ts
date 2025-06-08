import { RenderMode, ServerRoute } from '@angular/ssr';

export const serverRoutes: ServerRoute[] = [
  { path: 'concursante/:id', renderMode: RenderMode.Server },
  { path: 'subirFoto/:id', renderMode: RenderMode.Server },
  { path: 'concCuenta/:id', renderMode: RenderMode.Server },
  { path: 'fotosConc/:id', renderMode: RenderMode.Server },
  { path: 'juez/:id', renderMode: RenderMode.Server },
  { path: 'fotosJuez/:id', renderMode: RenderMode.Server },
  { path: 'puntuarFotos/:id', renderMode: RenderMode.Server },
  { path: 'juezCuenta/:id', renderMode: RenderMode.Server },
  { path: 'puntuacionPublicacion/:id', renderMode: RenderMode.Server },

  { path: '', renderMode: RenderMode.Prerender },
  { path: 'registro', renderMode: RenderMode.Prerender },
  { path: 'login', renderMode: RenderMode.Prerender },
  { path: 'admin', renderMode: RenderMode.Prerender },
  { path: 'adminUsuarios', renderMode: RenderMode.Prerender },
  { path: 'adminFotos', renderMode: RenderMode.Prerender },
  { path: 'gestionarValidaciones', renderMode: RenderMode.Prerender },
  { path: 'estadisticas', renderMode: RenderMode.Prerender },

  // Fallback
  { path: '**', renderMode: RenderMode.Prerender }
];
