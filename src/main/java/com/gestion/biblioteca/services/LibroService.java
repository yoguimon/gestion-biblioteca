package com.gestion.biblioteca.services;

import com.gestion.biblioteca.dao.LibroRepository;
import com.gestion.biblioteca.dao.UsuarioRepository;
import com.gestion.biblioteca.dto.DtoLibro;
import com.gestion.biblioteca.dto.DtoPrestarLibro;
import com.gestion.biblioteca.models.Libro;
import com.gestion.biblioteca.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibroService {
    @Autowired
    private LibroRepository libroRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    public String agregarNuevoLibro(DtoLibro dtoLibro){
        Libro libro = new Libro();
        libro.setTitulo(dtoLibro.getTitulo());
        libro.setAutor(dtoLibro.getAutor());
        libro.setISBN(dtoLibro.getIsbn());
        libro.setEstado((byte) 0);
        libroRepository.save(libro);
        return "Libro agregado correctamente";
    }

    public boolean prestarLibroAUsuario(DtoPrestarLibro dtoPrestarLibro) {
        Optional<Libro> optionalLibro = libroRepository.findById(dtoPrestarLibro.getIdLibro());
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(dtoPrestarLibro.getIdUsuario());
        if(optionalLibro.isPresent()&&optionalUsuario.isPresent()){
            Libro libro = optionalLibro.get();
            libro.setEstado((byte)1);
            libro.setUsuario(optionalUsuario.get());
            libroRepository.save(libro);
            return true;
        }else{
            return false;
        }
    }

    public void devolverLibroABiblioteca(Long id) {
        Optional<Libro> optionalLibro = libroRepository.findById(id);
        if (optionalLibro.isEmpty()) {
            throw new RuntimeException("Libro con id:"+id+" no encontrado");
        }
        Libro libro = optionalLibro.get();
        libro.setEstado((byte) 0);
        libro.setUsuario(null);
        libroRepository.save(libro);

    }

    public List<Libro> buscarLibrosPorTituloAutorIsbn(String palabra) {
        return libroRepository.findByTituloContainingIgnoreCaseOrAutorContainingIgnoreCaseOrISBNContainingIgnoreCase(palabra,palabra,palabra);
    }
    //opcion con query
    public List<Libro> buscarLibrosPorTituloAutorIsbn2(String palabra) {
        return libroRepository.filtroLibroPor(palabra);
    }

    public List<Libro> obtenerTodosLibros(){
        return libroRepository.findAll();
    }
    public List<Libro> obtenerTodosLibrosDisponibles(byte estado) {
        return libroRepository.findByEstado(estado);
    }

    public List<Libro> obtenerTodosLibrosPrestados(byte estado) {
        return libroRepository.findByEstado(estado);
    }
    //Long idd =1L;
    //Libro pp = libroRepository.findById(idd).orElseThrow(() -> new RuntimeException("Libro no encontrado"));
}
