package com.example.rallypicsapi.servicios;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.rallypicsapi.modelos.Juez;
import com.example.rallypicsapi.modelos.Usuario;
import com.example.rallypicsapi.repositorios.JuezRepositorio;

@Service
public class JuezServicio {

    private final Logger log = LoggerFactory.getLogger(JuezServicio.class);

    @Autowired
    private JuezRepositorio juezRepositorio;

    public Optional<Juez> getJuezById(Long id) {
        log.info("Buscando juez con ID: " + id);
        return this.juezRepositorio.findById(id);
    }

    public Juez getJuezByUsuario(Usuario usuario){
        log.info("Buscando juez por usuario: " + usuario.getEmail());
        return this.juezRepositorio.findByUsuario(usuario).size() == 0 ? null : this.juezRepositorio.findByUsuario(usuario).get(0);
    }

    public Optional<String> getEmailById(Long idJuez){
        log.info("Buscando email del juez con ID: " + idJuez);
        return this.juezRepositorio.findEmailById(idJuez);
    }
    
    public Optional<String> getContrasenaByEmail(String email){
        log.info("Buscando contraseña del juez con email: " + email);
        return this.juezRepositorio.findContrasenaByEmail(email);
    }

    public Juez upsertJuez(Juez juez){
        log.info("Creando o actualizando juez: " + juez.getUsuario().getEmail());
        return this.juezRepositorio.save(juez);
    }

    public void deleteJuez(Juez juez) {
        log.info("Eliminando juez con ID: " + juez.toString());
        this.juezRepositorio.delete(juez);
    }

    public Long getIdByUsuarioId(Long usuarioId) {
        return juezRepositorio.findIdByUsuarioId(usuarioId);
    }

    public List<Juez> getAllJueces() {
        return juezRepositorio.findAll();
    }
}
