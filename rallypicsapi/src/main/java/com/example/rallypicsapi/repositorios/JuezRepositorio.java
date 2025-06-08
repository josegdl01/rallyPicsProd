package com.example.rallypicsapi.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.rallypicsapi.modelos.Juez;
import com.example.rallypicsapi.modelos.Usuario;

import java.util.List;
import java.util.Optional;


@Repository
public interface JuezRepositorio extends JpaRepository<Juez, Long> {
    public List<Juez> findByUsuario(Usuario usuario);

    @Query("SELECT u.email AS email " +
           "FROM Juez j " +
           "JOIN j.usuario u " +
           "WHERE j.id = :idJuez")
    Optional<String> findEmailById(@Param("idJuez") Long idJuez);

    @Query("SELECT u.password AS contrasena " +
           "FROM Juez j " +
           "JOIN j.usuario u " +
           "WHERE u.email = :email")
    Optional<String> findContrasenaByEmail(@Param("email") String email);

    @Query("SELECT j.id FROM Juez j WHERE j.usuario.id = :usuarioId")
    Long findIdByUsuarioId(@Param("usuarioId") Long usuarioId);
}
