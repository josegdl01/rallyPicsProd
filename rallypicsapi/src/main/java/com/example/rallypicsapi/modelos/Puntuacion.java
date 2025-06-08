package com.example.rallypicsapi.modelos;

import jakarta.persistence.*;

@Entity
@Table(name = "puntuacion",
       uniqueConstraints = @UniqueConstraint(
           name = "unica_puntuacion_por_juez",
           columnNames = {"juez_id", "publicacion_id"}
       ))
public class Puntuacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "juez_id", nullable = false)
    private Juez juez;

    @ManyToOne(optional = false)
    @JoinColumn(name = "publicacion_id", nullable = false)
    private Publicacion publicacion;

    @Column(nullable = false)
    private Integer nota;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Juez getJuez() {
        return juez;
    }

    public void setJuez(Juez juez) {
        this.juez = juez;
    }

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((juez == null) ? 0 : juez.hashCode());
        result = prime * result + ((publicacion == null) ? 0 : publicacion.hashCode());
        result = prime * result + ((nota == null) ? 0 : nota.hashCode());
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
        Puntuacion other = (Puntuacion) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (juez == null) {
            if (other.juez != null)
                return false;
        } else if (!juez.equals(other.juez))
            return false;
        if (publicacion == null) {
            if (other.publicacion != null)
                return false;
        } else if (!publicacion.equals(other.publicacion))
            return false;
        if (nota == null) {
            if (other.nota != null)
                return false;
        } else if (!nota.equals(other.nota))
            return false;
        return true;
    }

    
}
