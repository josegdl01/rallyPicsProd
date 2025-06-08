package com.example.rallypicsapi.controladores;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rallypicsapi.modelos.Juez;
import com.example.rallypicsapi.modelos.Usuario;
import com.example.rallypicsapi.servicios.JuezServicio;
import com.example.rallypicsapi.servicios.PuntuacionServicio;
import com.example.rallypicsapi.servicios.UsuarioServicio;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/juez")
@CrossOrigin(origins = "http://localhost:4200")
public class JuezControlador {

    private final Logger log = LoggerFactory.getLogger(JuezControlador.class);

    @Autowired
    private JuezServicio juezServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private PuntuacionServicio puntuacionServicio;

    @GetMapping("/getJuez")
    public ResponseEntity<?> getJuez(@RequestParam Long id) {
        Optional<Juez> juez = juezServicio.getJuezById(id);

        if (juez.isPresent()) {
            return new ResponseEntity<>(juez.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Juez no encontrado", HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/deleteJuez/{id}")
    public ResponseEntity<?> deleteJuez(@PathVariable Long id) {
        Optional<Juez> juez = juezServicio.getJuezById(id);

        if(juez.isPresent()) {
            log.info("Eliminando al juez: " + juez.get());
            juezServicio.deleteJuez(juez.get());
            return new ResponseEntity<>(Collections.singletonMap("mensaje", "Juez eliminado correctamente"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Collections.singletonMap("mensaje", "Juez no encontrado"), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateJuez")
    public ResponseEntity<?> updateJuez(@RequestBody Juez juez) {
        Optional<Juez> juezBd = juezServicio.getJuezById(juez.getId());
        Optional<Usuario> usuarioBd = usuarioServicio.selectById(juez.getUsuario().getId());

        if(juezBd.isPresent() && usuarioBd.isPresent()) {
            log.info("Actualizando juez: " + juez.toString());
            juez.getUsuario().setEsJuez(juezBd.get().getUsuario().getEsJuez());

            Usuario usuarioActualizado = usuarioServicio.upsertUsuario(juez.getUsuario());
            juez.setUsuario(usuarioActualizado);

            Juez updatedJuez = juezServicio.upsertJuez(juez);
            return new ResponseEntity<>(updatedJuez, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Juez no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getIdByUsuario/{usuarioId}")
    public ResponseEntity<?> getJuezIdByUsuarioId(@PathVariable Long usuarioId) {
        Long id = juezServicio.getIdByUsuarioId(usuarioId);
        if (id != null) {
            return new ResponseEntity<>(id, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Juez no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAllJueces")
    public ResponseEntity<?> getAllJueces() {
        List<Juez> jueces = juezServicio.getAllJueces();
        return new ResponseEntity<>(jueces, HttpStatus.OK);
    }
}
