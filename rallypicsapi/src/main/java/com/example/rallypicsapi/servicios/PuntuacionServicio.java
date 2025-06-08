package com.example.rallypicsapi.servicios;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.rallypicsapi.modelos.Puntuacion;
import com.example.rallypicsapi.repositorios.PuntuacionRepositorio;

@Service
public class PuntuacionServicio {

    private final Logger log = LoggerFactory.getLogger(PuntuacionServicio.class);

    @Autowired
    private PuntuacionRepositorio puntuacionRepositorio;

    public List<Puntuacion> getPuntuacionesByJuez(Long juezId) {
        log.info("Consultando puntuaciones para el juez con ID: ", juezId);
        return puntuacionRepositorio.findByJuezId(juezId);
    }

    public List<Puntuacion> getPuntuacionesByPublicacion(Long publicacionId) {
        return puntuacionRepositorio.findByPublicacionId(publicacionId);
    }

    public Puntuacion insertarPuntuacion(Puntuacion puntuacion) {
        return puntuacionRepositorio.save(puntuacion);
    }

    public void deleteByUsuarioId(Long usuarioId) {
        puntuacionRepositorio.deleteByJuezId(usuarioId);
    }

    public List<Object []> obtenerRankingConcursantes() {
        log.info("Obteniendo ranking de concursantes");
        return puntuacionRepositorio.obtenerRankingConcursantes();
    }
}
