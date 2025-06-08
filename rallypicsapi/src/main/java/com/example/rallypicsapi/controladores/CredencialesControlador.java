package com.example.rallypicsapi.controladores;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rallypicsapi.configuracion.ConfigSeguridad;
import com.example.rallypicsapi.dto.ConcursanteDTO;
import com.example.rallypicsapi.dto.CredencialesDTO;
import com.example.rallypicsapi.dto.JuezDTO;
import com.example.rallypicsapi.dto.JwtDTO;
import com.example.rallypicsapi.modelos.Concursante;
import com.example.rallypicsapi.modelos.Juez;
import com.example.rallypicsapi.modelos.Usuario;
import com.example.rallypicsapi.servicios.ConcursanteServicio;
import com.example.rallypicsapi.servicios.CredencialesServicio;
import com.example.rallypicsapi.servicios.JuezServicio;
import com.example.rallypicsapi.servicios.UsuarioServicio;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class CredencialesControlador {

    private final Logger log = LoggerFactory.getLogger(CredencialesControlador.class);


    @Autowired
    private CredencialesServicio credsServ;

    @Autowired
    private UsuarioServicio usuarioServ;

    @Autowired
    private ConcursanteServicio concursanteServ;

    @Autowired
    private JuezServicio juezServ;

    @PostMapping("/registroJuez")
    public ResponseEntity<?> registroJuez(@RequestBody Juez juez) {
        Juez juezBd = this.juezServ.getJuezByUsuario(juez.getUsuario());
        Concursante concursanteBd = this.concursanteServ.getConcursanteByUsuario(juez.getUsuario());

        if(!Objects.isNull(juezBd)){
            return new ResponseEntity<>("Ya existe un juez asociado a este usuario.",
                        HttpStatus.CONFLICT);
        } else if(!Objects.isNull(concursanteBd)){
            return new ResponseEntity<>("Lo sentimos, pero un concursante no puede darse de alta como juez.",
                        HttpStatus.CONFLICT);
        } else {
            juez.setId(null);
            juez.getUsuario().setId(null);
            juez.getUsuario().setEsJuez(true); 
            String contra = juez.getUsuario().getPassword();
            String encriptada = ConfigSeguridad.getPasswordEncoder().encode(contra);
            juez.getUsuario().setPassword(encriptada);
            juez.setUsuario(this.usuarioServ.upsertUsuario(juez.getUsuario()));
            return new ResponseEntity<Juez>(this.juezServ.upsertJuez(juez),
                        HttpStatus.OK);
        }
    }
    
    @PostMapping("/registroConc")
    public ResponseEntity<?> registroConc(@RequestBody Concursante concursante) {
        Concursante concusanteBd = this.concursanteServ.getConcursanteByUsuario(concursante.getUsuario()) != null ? this.concursanteServ.getConcursanteByUsuario(concursante.getUsuario()) : null;
        Juez juezBd = this.juezServ.getJuezByUsuario(concursante.getUsuario()) != null ? this.juezServ.getJuezByUsuario(concursante.getUsuario()) : null;

        if(!Objects.isNull(concusanteBd)){
            return new ResponseEntity<>("Ya existe un concursante asociado a este usuario.",
                        HttpStatus.CONFLICT);
        } else if(!Objects.isNull(juezBd)){
            return new ResponseEntity<>("Lo sentimos, pero un juez no puede darse de alta como concursante.",
                        HttpStatus.CONFLICT);
        } else {
            concursante.setId(null);
            concursante.getUsuario().setId(null);
            concursante.getUsuario().setEsJuez(false);
            String contra = concursante.getUsuario().getPassword();
            log.info("Usuario recibido " + concursante.getUsuario().toString());
            String encriptada = ConfigSeguridad.getPasswordEncoder().encode(contra);
            concursante.getUsuario().setPassword(encriptada);
            concursante.setUsuario(this.usuarioServ.upsertUsuario(concursante.getUsuario()));
            return new ResponseEntity<Concursante>(this.concursanteServ.createConcursante(concursante),
                        HttpStatus.OK);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody CredencialesDTO credenciales) {
        Optional<Usuario> usuario = this.usuarioServ.getUsuarioByEmail(credenciales.getEmail());
        if(usuario.isPresent()){ 
            log.info("recibe" + credenciales.getContrasenya());
            if(ConfigSeguridad.getPasswordEncoder().matches(credenciales.getContrasenya(), usuario.get().getPassword())){
                log.info("Usuario encontrado: " + usuario.get().toString());
                if(usuario.get().getEsJuez() == null){
                    return new ResponseEntity<>(new JwtDTO(this.credsServ.crearJWTLogin(usuario.get())), 
                                                HttpStatus.OK);
                } else if(usuario.get().getEsJuez()){
                    Juez juez = this.juezServ.getJuezByUsuario(usuario.get());
                    JuezDTO juezDTO = new JuezDTO();
                    juezDTO.setId(juez.getId());
                    juezDTO.setUsuario(juez.getUsuario().getId());
                    juezDTO.setPuedeValidar(juez.getPuedeValidar());
                    
                    return new ResponseEntity<>(new JwtDTO(this.credsServ.crearJWTLogin(juezDTO)), 
                    HttpStatus.OK);
                } else {
                    Concursante concursante = this.concursanteServ.getConcursanteByUsuario(usuario.get());
                    ConcursanteDTO concursanteDTO = new ConcursanteDTO();
                    concursanteDTO.setId(concursante.getId());
                    concursanteDTO.setUsuario(concursante.getUsuario().getId());
                    
                    return new ResponseEntity<>(new JwtDTO(this.credsServ.crearJWTLogin(concursanteDTO)), 
                    HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>("La contraseña introducida es errónea.",
                        HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>("No hemos podido encontrar ningún usuario asociado a ese email.",
                        HttpStatus.NOT_FOUND);   
        }
    }
    
    @RequestMapping("/comprobarContra")
    public ResponseEntity<?> comprobarContra(@RequestParam String contra, @RequestParam Long id) {
    Optional<Usuario> usuario = this.usuarioServ.selectById(id);

    if(usuario.isPresent()){
        if(ConfigSeguridad.getPasswordEncoder().matches(contra, usuario.get().getPassword())){
            return ResponseEntity.ok(Collections.singletonMap("status", "OK"));
        } else {
            return ResponseEntity.ok(Collections.singletonMap("status", "NOTOK"));

        }
    } else {
            return ResponseEntity.ok(Collections.singletonMap("status", "ERROR"));

    }
    }
}
