package com.example.rallypicsapi.controladores;

import com.example.rallypicsapi.modelos.Puntuacion;
import com.example.rallypicsapi.servicios.PuntuacionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/puntuacion")
@CrossOrigin(origins = "http://localhost:4200")
public class PuntuacionControlador {

    @Autowired
    private PuntuacionServicio puntuacionServicio;

    @GetMapping("/getByJuez/{juezId}")
    public ResponseEntity<?> getPuntuacionesByJuez(@PathVariable Long juezId) {
        try {
            return ResponseEntity.ok(puntuacionServicio.getPuntuacionesByJuez(juezId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener puntuaciones");
        }
    }

    @GetMapping("/getByPublicacion/{publicacionId}")
    public ResponseEntity<?> getPuntuacionesByPublicacion(@PathVariable Long publicacionId) {
        try {
            return ResponseEntity.ok(puntuacionServicio.getPuntuacionesByPublicacion(publicacionId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener puntuaciones");
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<?> insertarPuntuacion(@RequestBody Puntuacion puntuacion) {
        try {
            Puntuacion guardada = puntuacionServicio.insertarPuntuacion(puntuacion);
            return ResponseEntity.ok(guardada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al insertar puntuación: " + e.getMessage());
        }
    }

    @GetMapping("/rankingConcursantes")
    public ResponseEntity<?> getRankingConcursantes() {
        List<Object[]> ranking = puntuacionServicio.obtenerRankingConcursantes();
        if(!ranking.isEmpty()){
            return new ResponseEntity<>(ranking, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No se han obtenido puntuaciones", HttpStatus.NOT_FOUND);
        }
    }
}
