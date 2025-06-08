package com.example.rallypicsapi.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.rallypicsapi.modelos.Concursante;
import com.example.rallypicsapi.modelos.Publicacion;

public interface PublicacionRepositorio extends CrudRepository<Publicacion, Long> {
    public List<Publicacion> findByConcursante(Concursante concursante);
    public List<Publicacion> findByValidada(boolean validada);

    @Modifying
    @Transactional
    @Query("DELETE FROM Publicacion p WHERE p.concursante.id = :usuarioId")
    void deleteByConcursanteId(Long usuarioId);
}
