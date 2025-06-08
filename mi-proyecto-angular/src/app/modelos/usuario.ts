export interface Usuario {
    id: number;
    nombre: string;
    apellidos: string;
    email: string;
    password: string;
    fechaNacimiento: Date;
    esJuez: boolean;
}
