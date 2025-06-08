package com.example.rallypicsapi.repositorios;

import com.example.rallypicsapi.modelos.Puntuacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PuntuacionRepositorio extends CrudRepository<Puntuacion, Long> {
    List<Puntuacion> findByJuezId(Long juezId);

    @Query("SELECT p.publicacion.id FROM Puntuacion p WHERE p.juez.id = :juezId")
    List<Long> findPublicacionIdsPuntuadasPorJuez(Long juezId);

    List<Puntuacion> findByPublicacionId(Long publicacionId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Puntuacion p WHERE p.juez.id = :usuarioId")
    void deleteByJuezId(Long usuarioId);

    @Query("SELECT p.publicacion.concursante.usuario.nombre, AVG(p.nota) as media, COUNT(p.id) as total " +
           "FROM Puntuacion p " +
           "GROUP BY p.publicacion.concursante.usuario.id, p.publicacion.concursante.usuario.nombre " +
           "ORDER BY media DESC")
    List<Object[]> obtenerRankingConcursantes();
}