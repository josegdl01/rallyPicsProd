package com.example.rallypicsapi.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.rallypicsapi.modelos.Usuario;


@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    public Optional<Usuario> findByEmail(String email);
    public Optional<String> findContrasenaById(Long id);
}
