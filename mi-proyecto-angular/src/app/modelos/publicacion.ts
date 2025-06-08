import { Concursante } from "./concursante";

export interface Publicacion {
    id: number;
    descripcion: string;
    concursante: Concursante;
    validada: boolean;
    foto: File;
}
