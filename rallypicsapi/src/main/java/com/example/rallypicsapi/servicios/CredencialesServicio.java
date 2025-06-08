package com.example.rallypicsapi.servicios;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.rallypicsapi.configuracion.JWTProvider;
import com.example.rallypicsapi.configuracion.ConfigSeguridad;
import com.example.rallypicsapi.dto.ConcursanteDTO;
import com.example.rallypicsapi.dto.JuezDTO;
import com.example.rallypicsapi.modelos.Usuario;

@Service
public class CredencialesServicio{

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private final JWTProvider jwtProvider;

    public CredencialesServicio() {
        this.jwtProvider = new JWTProvider();
    }

    public boolean registro(Usuario usuario) {
        boolean registrado = false;
        Optional<Usuario> usuarioBd = usuarioServicio.getUsuarioByEmail(usuario.getEmail());
        if (!usuarioBd.isPresent()) {
            usuario.setPassword(ConfigSeguridad.getPasswordEncoder().encode(usuario.getPassword()));
            usuarioServicio.upsertUsuario(usuario);
        }
        return registrado;
    }

    public String crearJWTLogin(Object objectDTO) {
        if (objectDTO instanceof JuezDTO) {
            JuezDTO juezDTO = (JuezDTO) objectDTO;
            return jwtProvider.crearToken(juezDTO);
        } else if(objectDTO instanceof ConcursanteDTO) {
            ConcursanteDTO concursanteDTO = (ConcursanteDTO) objectDTO;
            return jwtProvider.crearToken(concursanteDTO);
        } else {
            Usuario usuario = (Usuario) objectDTO;
            return jwtProvider.crearToken(usuario);
        }
    }
}

