package com.example.rallypicsapi.dto;

public class PublicacionDTO {
    private Long id;
    private String descripcion;
    private String fotoBase64;
    private Long concursanteId;
    private Boolean validada;

    public PublicacionDTO() {}
    
    public PublicacionDTO(Long id, String descripcion, String fotoBase64, Long concursanteId, Boolean validada) {
        this.id = id;
        this.descripcion = descripcion;
        this.fotoBase64 = fotoBase64;
        this.concursanteId = concursanteId;
        this.validada = validada;
    }

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
    public String getFotoBase64() {
        return fotoBase64;
    }
    public void setFotoBase64(String fotoBase64) {
        this.fotoBase64 = fotoBase64;
    }
    public Long getConcursanteId() {
        return concursanteId;
    }
    public void setConcursanteId(Long concursanteId) {
        this.concursanteId = concursanteId;
    }
    public Boolean getValidada() {
        return validada;
    }
    public void setValidada(Boolean validada) {
        this.validada = validada;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
        result = prime * result + ((fotoBase64 == null) ? 0 : fotoBase64.hashCode());
        result = prime * result + ((concursanteId == null) ? 0 : concursanteId.hashCode());
        result = prime * result + ((validada == null) ? 0 : validada.hashCode());
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
        PublicacionDTO other = (PublicacionDTO) obj;
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
        if (fotoBase64 == null) {
            if (other.fotoBase64 != null)
                return false;
        } else if (!fotoBase64.equals(other.fotoBase64))
            return false;
        if (concursanteId == null) {
            if (other.concursanteId != null)
                return false;
        } else if (!concursanteId.equals(other.concursanteId))
            return false;
        if (validada == null) {
            if (other.validada != null)
                return false;
        } else if (!validada.equals(other.validada))
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "PublicacionDTO [id=" + id + ", descripcion=" + descripcion + ", fotoBase64=" + fotoBase64
                + ", concursanteId=" + concursanteId + ", validada=" + validada + "]";
    }
} 
