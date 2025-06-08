package com.example.rallypicsapi.modelos;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "configuracion")
public class Configuracion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_cerrado", nullable = false)
    private boolean isCerrado;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    
    public boolean isCerrado() {
        return isCerrado;
    }

    public void setCerrado(boolean estadoSorteo) {
        this.isCerrado = estadoSorteo;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + (isCerrado ? 1231 : 1237);
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
        Configuracion other = (Configuracion) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (isCerrado != other.isCerrado)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Configuracion [id=" + id + ", estadoSorteo=" + isCerrado + "]";
    }
    
    
}