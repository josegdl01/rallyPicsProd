import { Concursante } from "./concursante";

export interface Publicacion64 {
    id: number;
    descripcion: string;
    concursante: Concursante;
    validada: boolean;
    foto: String;
}
