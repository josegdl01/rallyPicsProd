package com.example.rallypicsapi.dto;

public class JuezDTO {

    private Long id;

    private Long usuario;

    private Boolean puedeValidar;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuario() {
        return usuario;
    }

    public void setUsuario(Long usuario) {
        this.usuario = usuario;
    }

    public Boolean getPuedeValidar() {
        return puedeValidar;
    }

    public void setPuedeValidar(Boolean puedeValidar) {
        this.puedeValidar = puedeValidar;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
        result = prime * result + ((puedeValidar == null) ? 0 : puedeValidar.hashCode());
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
        JuezDTO other = (JuezDTO) obj;
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
        if (puedeValidar == null) {
            if (other.puedeValidar != null)
                return false;
        } else if (!puedeValidar.equals(other.puedeValidar))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "JuezDTO [id=" + id + ", usuario=" + usuario + ", puedeValidar=" + puedeValidar + "]";
    }
}
