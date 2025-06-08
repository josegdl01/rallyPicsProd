package com.example.rallypicsapi.dto;

import java.io.Serializable;

public class JwtDTO implements Serializable{
    private String token;

    public JwtDTO() {
    }

    public JwtDTO(String token) {
        this.token = token;
    }   

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
