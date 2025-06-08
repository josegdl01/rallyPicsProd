package com.example.rallypicsapi.modelos;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "juez")
public class Juez {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @Column(name = "puede_validar", nullable = false)
    private boolean puedeValidar;
    
    @OneToMany(mappedBy = "juez", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<Puntuacion> puntuaciones;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean getPuedeValidar() {
        return puedeValidar;
    }

    public void setPuedeValidar(boolean puedeValidar) {
        this.puedeValidar = puedeValidar;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
        result = prime * result + (puedeValidar ? 1231 : 1237);
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
        Juez other = (Juez) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (usuario == null) {
            if (other.usuario != null)
                return false;
        } else if (!usuario.equals(other.usuario))
            return false;
        if (puedeValidar != other.puedeValidar)
            return false;
        return true;
    }

    
}
