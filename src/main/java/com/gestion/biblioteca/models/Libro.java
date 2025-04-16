package com.gestion.biblioteca.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "libro")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    private String titulo;
    private String autor;
    @Column(name = "isbn")
    private String ISBN;
    private byte estado=0;
}
