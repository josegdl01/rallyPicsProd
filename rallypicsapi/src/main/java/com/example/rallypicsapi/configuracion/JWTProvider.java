package com.example.rallypicsapi.configuracion;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.rallypicsapi.dto.ConcursanteDTO;
import com.example.rallypicsapi.dto.CredencialesDTO;
import com.example.rallypicsapi.dto.JuezDTO;
import com.example.rallypicsapi.modelos.Usuario;
import com.example.rallypicsapi.servicios.ConcursanteServicio;
import com.example.rallypicsapi.servicios.JuezServicio;


@Component
public class JWTProvider {

    //@Value("${jwt.claveSecreta}")
    private String claveSecreta="404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    @Autowired
    private ConcursanteServicio concursanteServ;

    @Autowired
    private JuezServicio juezServ;

    
    public String crearToken(JuezDTO juezDto) {

        String email = juezServ.getEmailById(juezDto.getId()).orElse(null);
        
        if (email != null) {
            Algorithm algoritmo = Algorithm.HMAC256(claveSecreta);
            Instant ahora = Instant.now();
    
            Instant limite = ahora.plus(1, ChronoUnit.HOURS);

            return JWT.create()
                .withSubject(Long.toString(juezDto.getId()))
                .withClaim("rol", "juez")
                .withIssuedAt(Date.from(ahora))
                .withExpiresAt(Date.from(limite))
                .sign(algoritmo);
        } else {
            throw new RuntimeException("No se ha encontrado el email del juez");
        }

    }

    public String crearToken(ConcursanteDTO concursanteDTO) {
        String email = concursanteServ.getEmailById(concursanteDTO.getId()).orElse(null);
        if(email != null){
            Algorithm algoritmo = Algorithm.HMAC256(claveSecreta);
            Instant ahora = Instant.now();
    
            Instant limite = ahora.plus(1, ChronoUnit.HOURS);
    
                return JWT.create()
                    .withSubject(Long.toString(concursanteDTO.getId()))
                    .withClaim("rol", "conc")
                    .withIssuedAt(Date.from(ahora))
                    .withExpiresAt(Date.from(limite))
                    .sign(algoritmo);
        } else {
            throw new RuntimeException("No se ha encontrado el email del concursante");
        }
    }

    public String crearToken(Usuario usuario) {
        Algorithm algoritmo = Algorithm.HMAC256(claveSecreta);
        Instant ahora = Instant.now();

        Instant limite = ahora.plus(1, ChronoUnit.HOURS);

            return JWT.create()
                .withSubject(Long.toString(usuario.getId()))
                .withClaim("rol", "admin")
                .withIssuedAt(Date.from(ahora))
                .withExpiresAt(Date.from(limite))
                .sign(algoritmo);
    }

    
    public Authentication validarToken(String token){
        Algorithm algorithm = Algorithm.HMAC256(claveSecreta);

        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJwt = verifier.verify(token);

        CredencialesDTO creds = new CredencialesDTO();
        creds.setEmail(decodedJwt.getSubject());

        return new UsernamePasswordAuthenticationToken(creds, null, null);
    }
}

