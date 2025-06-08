package com.example.rallypicsapi.controladores;

import com.example.rallypicsapi.servicios.ConfiguracionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/configuracion")
@CrossOrigin(origins = "http://localhost:4200")
public class ConfiguracionControlador {

    @Autowired
    private ConfiguracionServicio configuracionServicio;

    @GetMapping("/getConfiguracion/{id}")
    public ResponseEntity<?> getConfiguracion(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(configuracionServicio.getConfiguracion(id), HttpStatus.OK); 
        } catch (Exception e) {
            return new ResponseEntity<>("Error al obtener la configuración: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/cerrarSorteo")
    public ResponseEntity<?> cerrarSorteo() {
        configuracionServicio.cerrarSorteo();
        return ResponseEntity.ok(Collections.singletonMap("mensaje", "Sorteo cerrado"));
    }

    @GetMapping("/estadoSorteo")
    public ResponseEntity<?> getEstadoSorteo() {
        boolean cerrado = configuracionServicio.estaCerrado();
        return ResponseEntity.ok(Collections.singletonMap("cerrado", cerrado));
    }
}