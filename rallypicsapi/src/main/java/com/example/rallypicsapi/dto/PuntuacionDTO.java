package com.example.rallypicsapi.dto;

public class PuntuacionDTO {
    private Long id;
    private Long juezId;
    private Long publicacionId;
    private Integer nota;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getJuezId() {
        return juezId;
    }
    public void setJuezId(Long juezId) {
        this.juezId = juezId;
    }
    public Long getPublicacionId() {
        return publicacionId;
    }
    public void setPublicacionId(Long publicacionId) {
        this.publicacionId = publicacionId;
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
        result = prime * result + ((juezId == null) ? 0 : juezId.hashCode());
        result = prime * result + ((publicacionId == null) ? 0 : publicacionId.hashCode());
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
        PuntuacionDTO other = (PuntuacionDTO) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (juezId == null) {
            if (other.juezId != null)
                return false;
        } else if (!juezId.equals(other.juezId))
            return false;
        if (publicacionId == null) {
            if (other.publicacionId != null)
                return false;
        } else if (!publicacionId.equals(other.publicacionId))
            return false;
        if (nota == null) {
            if (other.nota != null)
                return false;
        } else if (!nota.equals(other.nota))
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "PuntuacionDTO [id=" + id + ", juezId=" + juezId +", publicacionId="
                + publicacionId + ", nota=" + nota + "]";
    }

    
}
