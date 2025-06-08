package com.example.rallypicsapi.servicios;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.rallypicsapi.modelos.Concursante;
import com.example.rallypicsapi.modelos.Usuario;
import com.example.rallypicsapi.repositorios.ConcursanteRepositorio;

@Service
public class ConcursanteServicio {

    private final Logger log = LoggerFactory.getLogger(ConcursanteServicio.class);

    @Autowired
    private ConcursanteRepositorio concursanteRepositorio;
    @Autowired
    private UsuarioServicio usuarioServicio;

    public Optional<Concursante> getConcursanteById(Long id){
        log.info("Buscando concursante con ID: " + id);
        return this.concursanteRepositorio.findById(id);
    }

    public Concursante getConcursanteByUsuario(Usuario usuario){
        return this.concursanteRepositorio.findByUsuario(usuario).size() == 0 ? null : this.concursanteRepositorio.findByUsuario(usuario).get(0);
    }

    public Optional<String> getEmailById(Long idConc){
        log.info("Buscando email del concursante con ID: " + this.concursanteRepositorio.findEmailById(idConc).get());
        return this.concursanteRepositorio.findEmailById(idConc);
    }

    public Optional<String> getContrasenaByEmail(String email){
        return this.concursanteRepositorio.findContrasenaByEmail(email);
    }

    public Concursante createConcursante(Concursante concursante){
        log.info("Creando concursante: " + concursante.toString());
        Concursante concursanteBd = this.concursanteRepositorio.save(concursante);
        log.info("Concursante creado: " + concursanteBd.toString());
        return concursanteBd;
        
    }

    public void deleteConcursante(Concursante concursante){
        log.info("Eliminando concursante: " + concursante.toString());
        this.concursanteRepositorio.delete(concursante);
        log.info("Concursante eliminado correctamente");
    }

    public Concursante updateConcursante(Concursante concursante) {
        log.info("Actualizando concursante: " + concursante.toString());
        Concursante updatedConcursante = this.concursanteRepositorio.save(concursante);
        concursante.getUsuario().setEsJuez(false);
        usuarioServicio.upsertUsuario(concursante.getUsuario());
        log.info("Concursante actualizado: " + updatedConcursante.toString());
        return updatedConcursante;
    }

    public Long getIdByUsuarioId(Long usuarioId) {
        return concursanteRepositorio.findIdByUsuarioId(usuarioId);
    }
}
