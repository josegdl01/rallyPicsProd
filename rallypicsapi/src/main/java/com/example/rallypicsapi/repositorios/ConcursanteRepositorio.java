package com.example.rallypicsapi.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.rallypicsapi.modelos.Concursante;
import com.example.rallypicsapi.modelos.Usuario;

@Repository
public interface ConcursanteRepositorio extends JpaRepository<Concursante, Long> {
    public List<Concursante> findByUsuario(Usuario usuario);

    @Query("SELECT u.email AS email " +
           "FROM Concursante c " +
           "JOIN c.usuario u " +
           "WHERE c.id = :idConc")
    Optional<String> findEmailById(@Param("idConc") Long idConc);

    @Query("SELECT u.password AS contrasena " +
           "FROM Concursante c " +
           "JOIN c.usuario u " +
           "WHERE u.email = :email")
    Optional<String> findContrasenaByEmail(@Param("email") String email);

    @Query("SELECT c.id FROM Concursante c WHERE c.usuario.id = :usuarioId")
    Long findIdByUsuarioId(@Param("usuarioId") Long usuarioId);
}
