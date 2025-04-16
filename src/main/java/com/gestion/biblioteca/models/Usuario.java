package com.gestion.biblioteca.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @Column(name = "dni")
    private String DNI;
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Libro> libros = new ArrayList<>();

}
