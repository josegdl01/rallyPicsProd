package com.example.rallypicsapi.modelos;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "concursante")
public class Concursante {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @OneToMany(mappedBy = "concursante", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Publicacion> fotos = new ArrayList<>();

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

    public List<Publicacion> getFotos() {
        return fotos;
    }

    public void setFotos(List<Publicacion> fotos) {
        this.fotos = fotos;
    }

    public void addFoto(Publicacion foto) {
        fotos.add(foto);
        foto.setConcursante(this);
    }

    public void quitarFoto(Publicacion foto) {
        fotos.remove(foto);
        foto.setConcursante(null);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
        result = prime * result + ((fotos == null) ? 0 : fotos.hashCode());
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
        Concursante other = (Concursante) obj;
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
        if (fotos == null) {
            if (other.fotos != null)
                return false;
        } else if (!fotos.equals(other.fotos))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Concursante [id=" + id + ", usuario=" + usuario + ", número de fotos=" + fotos.size() + "]";
    }

    
}
