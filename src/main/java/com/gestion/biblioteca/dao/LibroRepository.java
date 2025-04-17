package com.gestion.biblioteca.dao;

import com.gestion.biblioteca.models.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface LibroRepository extends JpaRepository<Libro,Long> {

    List<Libro> findByEstado(byte estado);
    List<Libro> findByTituloContainingIgnoreCaseOrAutorContainingIgnoreCaseOrISBNContainingIgnoreCase(String titulo, String autor, String isbn);
}
