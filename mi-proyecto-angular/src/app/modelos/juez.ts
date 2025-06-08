import { Publicacion } from "./publicacion";
import { Usuario } from "./usuario";

export interface Juez {
    id: number;
    usuario: Usuario;
    puedeValidar: boolean;
}
