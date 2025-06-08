package com.example.rallypicsapi.modelos;

import java.util.Arrays;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "publicacion")
public class Publicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String descripcion;

    @ManyToOne(optional = false)
    @JoinColumn(name = "concursante_id", nullable = false)
    private Concursante concursante;

    @Column(nullable = false)
    private boolean validada = false;

    @Lob
    @Column(nullable = false, columnDefinition = "LONGBLOB")
    private byte[] foto;

    @OneToMany(mappedBy = "publicacion", cascade = CascadeType.ALL, orphanRemoval = true)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<Puntuacion> puntuaciones;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Concursante getConcursante() {
        return concursante;
    }

    public void setConcursante(Concursante concursante) {
        this.concursante = concursante;
    }

    public boolean isValidada() {
        return validada;
    }

    public void setValidada(boolean validada) {
        this.validada = validada;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
        result = prime * result + ((concursante == null) ? 0 : concursante.hashCode());
        result = prime * result + (validada ? 1231 : 1237);
        result = prime * result + Arrays.hashCode(foto);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Publicacion other = (Publicacion) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (descripcion == null) {
            if (other.descripcion != null)
                return false;
        } else if (!descripcion.equals(other.descripcion))
            return false;
        if (concursante == null) {
            if (other.concursante != null)
                return false;
        } else if (!concursante.equals(other.concursante))
            return false;
        if (validada != other.validada)
            return false;
        if (!Arrays.equals(foto, other.foto))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Publicacion [id=" + id + ", descripcion=" + descripcion + ", concursante=" + concursante + ", validada="
                + validada + ", foto=" + Arrays.toString(foto) + "]";
    }
}
