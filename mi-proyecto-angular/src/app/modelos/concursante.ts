import { Publicacion } from "./publicacion";
import { Usuario } from "./usuario";

export interface Concursante {
    id: number;
    fotos: Publicacion[];
    usuario: Usuario
}
