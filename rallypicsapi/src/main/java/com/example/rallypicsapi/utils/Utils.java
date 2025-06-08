package com.example.rallypicsapi.utils;

import java.util.Base64;
import com.example.rallypicsapi.dto.PublicacionDTO;
import com.example.rallypicsapi.modelos.Publicacion;

public class Utils {

    public static PublicacionDTO PublictoDTO(Publicacion publicacion) {
        PublicacionDTO publicacionDTO = new PublicacionDTO();
        
        publicacionDTO.setId(publicacion.getId());
        publicacionDTO.setDescripcion(publicacion.getDescripcion());
        publicacionDTO.setConcursanteId(publicacion.getConcursante().getId());
        publicacionDTO.setValidada(publicacion.isValidada());
        publicacionDTO.setFotoBase64(Base64.getEncoder().encodeToString(publicacion.getFoto()));
        
        return publicacionDTO;
    }

    public static Publicacion DtoToPublic(PublicacionDTO dto) {
        Publicacion publicacion = new Publicacion();
        
        publicacion.setId(dto.getId());
        publicacion.setDescripcion(dto.getDescripcion());
        
        if (dto.getFotoBase64() != null) {
            byte[] fotoBytes = Base64.getDecoder().decode(dto.getFotoBase64());
            publicacion.setFoto(fotoBytes);
        }
        
        return publicacion;
    }
}
