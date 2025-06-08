package com.example.rallypicsapi.dto;

import java.io.Serializable;

public class CredencialesDTO implements Serializable{

    private String email;
    private String contrasenya;

    public CredencialesDTO(){}

    public String getEmail() {
        return email;
    }
    public void setEmail(String usuario) {
        this.email = usuario;
    }
    public String getContrasenya() {
        return contrasenya;
    }
    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((contrasenya == null) ? 0 : contrasenya.hashCode());
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
        CredencialesDTO other = (CredencialesDTO) obj;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (contrasenya == null) {
            if (other.contrasenya != null)
                return false;
        } else if (!contrasenya.equals(other.contrasenya))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "CredencialesDTO [usuario=" + email + ", contrasenya=" + contrasenya + "]";
    }

    
}
