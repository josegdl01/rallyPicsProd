package com.example.rallypicsapi.servicios;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.rallypicsapi.configuracion.ConfigSeguridad;
import com.example.rallypicsapi.modelos.Usuario;
import com.example.rallypicsapi.repositorios.UsuarioRepositorio;

@Service
public class UsuarioServicio {

    private final Logger log = LoggerFactory.getLogger(UsuarioServicio.class);

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    public List<Usuario> selectAll(){
        log.info("Buscando todos los usuarios");
        return usuarioRepositorio.findAll();
    }

    public Optional<Usuario> selectById(Long id) {
        log.info("Buscando usuario con ID: ", id);
        return usuarioRepositorio.findById(id);
    }

    public Optional<Usuario> getUsuarioByEmail(String email) {
        log.info("Buscando usuario con email: ", email);
        return usuarioRepositorio.findByEmail(email);
    }

    public Usuario upsertUsuario(Usuario usuario) {
        log.info("Guardando usuario:", usuario);
        if (usuario.getPassword() != null && !usuario.getPassword().startsWith("$2")) {
        usuario.setPassword(ConfigSeguridad.getPasswordEncoder().encode(usuario.getPassword()));
        }
        return usuarioRepositorio.save(usuario);
    }

    public void deleteById(Long id) {
        log.info("Eliminando usuario con ID: ", id);
        usuarioRepositorio.deleteById(id);
    }

    public Optional<String> getContrasenaById(Long id) {
        log.info("Buscando contraseña del usuario con ID: ", id);
        return usuarioRepositorio.findContrasenaById(id);
    }
}
