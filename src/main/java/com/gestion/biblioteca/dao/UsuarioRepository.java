package com.gestion.biblioteca.dao;

import com.gestion.biblioteca.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
}
