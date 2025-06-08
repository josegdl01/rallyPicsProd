package com.example.rallypicsapi.controladores;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import com.example.rallypicsapi.dto.PublicacionDTO;
import com.example.rallypicsapi.modelos.Concursante;
import com.example.rallypicsapi.modelos.Publicacion;
import com.example.rallypicsapi.servicios.ConcursanteServicio;
import com.example.rallypicsapi.servicios.PublicacionServicio;
import com.example.rallypicsapi.utils.Utils;

@RestController
@RequestMapping("/publicacion")
@CrossOrigin(origins = "http://localhost:4200")
public class PublicacionControlador {

    @Autowired
    PublicacionServicio publicacionService;

    @Autowired
    ConcursanteServicio concursanteService;

    @PostMapping(value = "/subirPublicacion", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addPublicacion(
            @RequestParam("descripcion") String descripcion,
            @RequestParam("foto") MultipartFile foto,
            @RequestParam("concursanteId") Long concursanteId
    ) throws IOException {
        Optional<Concursante> concursante = concursanteService.getConcursanteById(concursanteId);
        if (!concursante.isPresent()) {
            return new ResponseEntity<>("Concursante no encontrado", HttpStatus.BAD_REQUEST);
        } else if(descripcion == null || descripcion.isEmpty()) {
            return new ResponseEntity<>("La descripción no puede estar vacía", HttpStatus.BAD_REQUEST);
        } else if(foto == null || foto.isEmpty()) {
            return new ResponseEntity<>("La foto no puede estar vacía", HttpStatus.BAD_REQUEST);
        } else {
            Publicacion publicacion = new Publicacion();
            publicacion.setDescripcion(descripcion);
            publicacion.setValidada(false);
            publicacion.setFoto(foto.getBytes());
            publicacion.setConcursante(concursante.get());
    
            return new ResponseEntity<>(publicacionService.insertPublicacion(publicacion), HttpStatus.CREATED);
        }
    }

    @GetMapping("/getPublicacionesByConc/{idConc}")
    public ResponseEntity<?> getPublicacionesByConcursante(@PathVariable Long idConc) {
        Optional<Concursante> concursante = concursanteService.getConcursanteById(idConc);
        if (concursante.isPresent()) {
            List<PublicacionDTO> publicaciones = new ArrayList<PublicacionDTO>();
            for(Publicacion publicacion : publicacionService.getPublicacionesByConcursante(concursante.get())){
                publicaciones.add(Utils.PublictoDTO(publicacion));
            }
            return new ResponseEntity<>(publicaciones, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Concursante no encontrado", HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/getPublicacionesSinValidar")
    public ResponseEntity<?> getPublicacionesSinValidar() {
        List<PublicacionDTO> publicaciones = new ArrayList<PublicacionDTO>();
        for(Publicacion publicacion : publicacionService.getPublicacionesByValidacion(false)){
            publicaciones.add(Utils.PublictoDTO(publicacion));
        }
        if(!publicaciones.isEmpty()) {
            return new ResponseEntity<>(publicaciones, HttpStatus.OK);
        }else {
            return new ResponseEntity<>("No hay publicaciones validadas", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getPublicacionesValidadas")
    public ResponseEntity<?> getPublicacionesValidadas() {
        List<PublicacionDTO> publicaciones = new ArrayList<PublicacionDTO>();
        for(Publicacion publicacion : publicacionService.getPublicacionesByValidacion(true)){
            publicaciones.add(Utils.PublictoDTO(publicacion));
        }
        if(!publicaciones.isEmpty()) {
            return new ResponseEntity<>(publicaciones, HttpStatus.OK);
        }else {
            return new ResponseEntity<>("No hay publicaciones validadas", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getPublicacionesValidadasNoPuntuadas/{juezId}")
    public ResponseEntity<?> getPublicacionesValidadasNoPuntuadas(@PathVariable Long juezId) {
        List<PublicacionDTO> publicaciones = new ArrayList<>();
        for (Publicacion publicacion : publicacionService.getPublicacionesValidadasNoPuntuadasPorJuez(juezId)) {
            publicaciones.add(Utils.PublictoDTO(publicacion));
        }
        if (!publicaciones.isEmpty()) {
            return new ResponseEntity<>(publicaciones, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No hay publicaciones validadas sin puntuar para este juez", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updatePublicacion/{id}")
    public ResponseEntity<?> updatePublicacion(@PathVariable Long id, @RequestBody PublicacionDTO publicacionDTO) {
        Optional<Publicacion> publicacionOpt = publicacionService.getPublicacionById(id);
        if (publicacionOpt.isPresent()) {
            Publicacion publicacion = publicacionOpt.get();
            publicacion.setDescripcion(publicacionDTO.getDescripcion());
            publicacion.setValidada(publicacionDTO.getValidada());

            Publicacion actualizada = publicacionService.updatePublicacion(publicacion);
            return new ResponseEntity<>(Utils.PublictoDTO(actualizada), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Publicación no encontrada", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAllPublicaciones")
    public ResponseEntity<?> getAllPublicaciones() {
        List<PublicacionDTO> publicaciones = new ArrayList<>();
        for (Publicacion publicacion : publicacionService.getAllPublicaciones()) {
            publicaciones.add(Utils.PublictoDTO(publicacion));
        }
        return new ResponseEntity<>(publicaciones, HttpStatus.OK);
    }

    @DeleteMapping("/deletePublicacion/{id}")
    public ResponseEntity<?> deletePublicacion(@PathVariable Long id) {
        Optional<Publicacion> publicacionOpt = publicacionService.getPublicacionById(id);
        if (publicacionOpt.isPresent()) {
            publicacionService.deletePublicacion(publicacionOpt.get());
            return new ResponseEntity<>("Publicación eliminada correctamente", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Publicación no encontrada", HttpStatus.NOT_FOUND);
        }
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
            .body("IMAGEN_DEMASIADO_GRANDE");
    }
}
