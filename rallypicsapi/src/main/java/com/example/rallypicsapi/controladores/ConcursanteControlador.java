package com.example.rallypicsapi.controladores;

import java.util.Collections;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.rallypicsapi.modelos.Concursante;
import com.example.rallypicsapi.modelos.Usuario;
import com.example.rallypicsapi.servicios.ConcursanteServicio;
import com.example.rallypicsapi.servicios.UsuarioServicio;



@RestController
@RequestMapping("/concursante")
@CrossOrigin(origins = "http://localhost:4200")
public class ConcursanteControlador {

    private final Logger log = LoggerFactory.getLogger(ConcursanteControlador.class);

    @Autowired
    private ConcursanteServicio concursanteServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/getConc")
    public ResponseEntity<?> getConcursanteById(@RequestParam Long id) {
        Optional<Concursante> concursante = concursanteServicio.getConcursanteById(id);

        if(concursante.isPresent()) {
            return new ResponseEntity<>(concursante.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Concursante no encontrado", HttpStatus.NOT_FOUND);

        }
    }
    
    @DeleteMapping("/deleteConcursante/{id}")
    public ResponseEntity<?> deleteConcursante(@PathVariable Long id) {
        Optional<Concursante> concursante = concursanteServicio.getConcursanteById(id);

        if(concursante.isPresent()) {
            log.info("Eliminando concursante: " + concursante.get());
            concursanteServicio.deleteConcursante(concursante.get());
            return new ResponseEntity<>(Collections.singletonMap("mensaje", "Concursante eliminado correctamente"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Collections.singletonMap("mensaje", "Concursante no encontrado"), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateConcursante")
    public ResponseEntity<?> updateConcursante(@RequestBody Concursante concursante) {
        Optional<Concursante> concursanteBd = concursanteServicio.getConcursanteById(concursante.getId());
        Optional<Usuario> usuarioBd = usuarioServicio.selectById(concursante.getUsuario().getId());

        if(concursanteBd.isPresent() && usuarioBd.isPresent()) {
            log.info("Actualizando concursante: " + concursante.toString());
            concursante.getUsuario().setEsJuez(concursanteBd.get().getUsuario().getEsJuez());
            concursante.setFotos(concursanteBd.get().getFotos());

            Usuario usuarioActualizado = usuarioServicio.upsertUsuario(concursante.getUsuario());
            concursante.setUsuario(usuarioActualizado);

            Concursante updatedConcursante = concursanteServicio.updateConcursante(concursante);
            return new ResponseEntity<>(updatedConcursante, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Concursante no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getIdByUsuario/{usuarioId}")
    public ResponseEntity<?> getConcursanteIdByUsuarioId(@PathVariable Long usuarioId) {
        Long id = concursanteServicio.getIdByUsuarioId(usuarioId);
        log.info("concursanteServicio.getIdByUsuarioId devuelve" + id);
        if (id != null) {
            return new ResponseEntity<>(id, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Concursante no encontrado", HttpStatus.NOT_FOUND);
        }
    }
}
