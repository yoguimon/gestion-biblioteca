package com.gestion.biblioteca.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

//@Data
public class DtoLibro {
    @Size(min = 3, message = "El titulo tiene que tener al menos 3 digitos")
    private String titulo;
    @Size(min = 3, message = "El autor tiene que tener al menos 3 digitos")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "El autor solo debe contener letras y espacios")
    private String autor;
    @Size(min = 15, message = "el isbn tiene un formato que tiene que ser de al menos 15 digitos")
    private String isbn;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
