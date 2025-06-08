package com.example.rallypicsapi.servicios;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.rallypicsapi.modelos.Concursante;
import com.example.rallypicsapi.modelos.Publicacion;
import com.example.rallypicsapi.repositorios.PublicacionRepositorio;
import com.example.rallypicsapi.repositorios.PuntuacionRepositorio;

@Service
public class PublicacionServicio {

    private final Logger log = LoggerFactory.getLogger(PublicacionServicio.class);

    @Autowired
    private PublicacionRepositorio publicacionRepositorio;

    @Autowired
    private PuntuacionRepositorio puntuacionRepositorio;

    public Publicacion insertPublicacion(Publicacion publicacion) {
        if (publicacion != null) {
            log.info("Insertando publicación: {}", publicacion);
            return publicacionRepositorio.save(publicacion);
        } else {
            log.warn("Intento de insertar una publicación nula");
            return null;
        }
    }

    public Optional<Publicacion> getPublicacionById(Long id) {
        log.info("Buscando publicación con id: " + id);
        return publicacionRepositorio.findById(id);
    }

    public List<Publicacion> getPublicacionesByConcursante(Concursante concursante) {
        log.info("Buscando publicaciones del concursante con id: " + concursante);
        return publicacionRepositorio.findByConcursante(concursante);
    }

    public List<Publicacion> getPublicacionesByValidacion(Boolean validada) {
        log.info("Buscando publicaciones validadas");
        return publicacionRepositorio.findByValidada(validada);
    }

    public Publicacion updatePublicacion(Publicacion publicacion) {
        log.info("Actualizando publicación: {}", publicacion);
        return publicacionRepositorio.save(publicacion);
    }

    public List<Publicacion> getPublicacionesValidadasNoPuntuadasPorJuez(Long juezId) {
        List<Long> idsPuntuadas = puntuacionRepositorio.findPublicacionIdsPuntuadasPorJuez(juezId);
        return publicacionRepositorio.findByValidada(true)
                .stream()
                .filter(pub -> !idsPuntuadas.contains(pub.getId()))
                .collect(Collectors.toList());
    }

    public List<Publicacion> getAllPublicaciones() {
        Iterable<Publicacion> iterable = publicacionRepositorio.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    public void deletePublicacion(Publicacion publicacion) {
        publicacionRepositorio.delete(publicacion);
    }

    public void deleteByUsuarioId(Long usuarioId) {
        publicacionRepositorio.deleteByConcursanteId(usuarioId);
    }
}