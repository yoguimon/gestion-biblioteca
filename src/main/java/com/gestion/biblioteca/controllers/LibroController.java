package com.gestion.biblioteca.controllers;

import com.gestion.biblioteca.dao.LibroRepository;
import com.gestion.biblioteca.dto.DtoLibro;
import com.gestion.biblioteca.dto.DtoPrestarLibro;
import com.gestion.biblioteca.models.Libro;
import com.gestion.biblioteca.services.LibroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/libro")
public class LibroController {
    @Autowired
    private LibroService libroService;
    @GetMapping("/obtener")
    private ResponseEntity<List<Libro>> obtenerLibros(){
        List<Libro> res = libroService.obtenerTodosLibros();
        if(res.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(res);
    }
    @GetMapping("/obtener/disponibles/{estado}")
    private ResponseEntity<List<Libro>> obtenerLibrosDisponibles(@PathVariable byte estado){
        List<Libro> res = libroService.obtenerTodosLibrosDisponibles(estado);
        if(res.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(res);
    }
    @GetMapping("/obtener/prestados/{estado}")
    private ResponseEntity<List<Libro>> obtenerLibrosPrestados(@PathVariable byte estado){
        List<Libro> res = libroService.obtenerTodosLibrosPrestados(estado);
        if(res.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(res);
    }
    @PostMapping("/agregar")
    private ResponseEntity<?> agregarLibro(@RequestBody @Valid DtoLibro dtoLibro, BindingResult result){
        if (result.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            for (FieldError error : result.getFieldErrors()){
                errores.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errores);
        }
        String mensaje = libroService.agregarNuevoLibro(dtoLibro);
        return ResponseEntity.ok(mensaje);
    }
    @PutMapping("/prestar")
    private ResponseEntity<?> prestarLibro(@RequestBody DtoPrestarLibro dtoPrestarLibro){
        boolean res = libroService.prestarLibroAUsuario(dtoPrestarLibro);
        if(res){
            return ResponseEntity.ok("El libro se presto con exito");
        }else{
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No se encontraron registros en la base de datos");
        }
    }
    @PutMapping("/devolver/{id}")
    private ResponseEntity<?> devolverLibro(@PathVariable Long id){
        try {
            libroService.devolverLibroABiblioteca(id);
            return ResponseEntity.ok("El libro se devolvió con éxito");
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}
