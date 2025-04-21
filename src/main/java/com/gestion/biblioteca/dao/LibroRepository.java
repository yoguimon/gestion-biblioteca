package com.gestion.biblioteca.dao;

import com.gestion.biblioteca.models.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface LibroRepository extends JpaRepository<Libro,Long> {

    List<Libro> findByEstado(byte estado);
    List<Libro> findByTituloContainingIgnoreCaseOrAutorContainingIgnoreCaseOrISBNContainingIgnoreCase(String titulo, String autor, String isbn);
    //misma consulta pero con query
    @Query("SELECT l FROM Libro l WHERE " +
            "LOWER(l.titulo) LIKE LOWER(CONCAT('%', :palabra, '%')) OR " +
            "LOWER(l.autor) LIKE LOWER(CONCAT('%', :palabra, '%')) OR " +
            "LOWER(l.ISBN) LIKE LOWER(CONCAT('%', :palabra, '%'))")
    List<Libro> filtroLibroPor(@Param("palabra") String palabra);
}
