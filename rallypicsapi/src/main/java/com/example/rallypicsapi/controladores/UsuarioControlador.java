package com.example.rallypicsapi.controladores;

import com.example.rallypicsapi.modelos.Usuario;
import com.example.rallypicsapi.servicios.UsuarioServicio;
import com.example.rallypicsapi.servicios.PuntuacionServicio;
import com.example.rallypicsapi.servicios.PublicacionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/allUsuarios")
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioServicio.selectAll();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }
}
