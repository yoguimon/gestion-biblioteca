package com.gestion.biblioteca;

import com.gestion.biblioteca.dao.LibroRepository;
import com.gestion.biblioteca.dao.UsuarioRepository;
import com.gestion.biblioteca.dto.DtoLibro;
import com.gestion.biblioteca.dto.DtoPrestarLibro;
import com.gestion.biblioteca.models.Libro;
import com.gestion.biblioteca.models.Usuario;
import com.gestion.biblioteca.services.LibroService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LibroServiceTest {
    @Mock
    private LibroRepository libroRepository; // Simula el repo
    @Mock
    private UsuarioRepository usuarioRepository;
    @InjectMocks
    private LibroService libroService; // Inyecta el mock
    @Test
    void agregarLibroDebeDevolverMensajeCorrecto(){
        DtoLibro dtoLibro = new DtoLibro();
        dtoLibro.setTitulo("No me puedes lastimar");
        dtoLibro.setAutor("David Goggins");
        dtoLibro.setIsbn("0-1106-6728-X");

        Libro libro = new Libro();
        libro.setTitulo(dtoLibro.getTitulo());
        libro.setAutor(dtoLibro.getAutor());
        libro.setISBN(dtoLibro.getIsbn());

        Mockito.when(libroRepository.save(Mockito.any(Libro.class))).thenReturn(libro);

        String mensajeRespuesta = libroService.agregarNuevoLibro(dtoLibro);
        assertEquals("Libro agregado correctamente", mensajeRespuesta);
        Mockito.verify(libroRepository).save(libro);
    }
    @Test
    void prestarLibroAUsuarioDebeRetornarTrueCuandoLibroYUsuarioExisten(){
        DtoPrestarLibro dto = new DtoPrestarLibro();
        dto.setIdLibro(1L);
        dto.setIdUsuario(1L);

        Libro libro = new Libro();
        libro.setId(1L);
        libro.setEstado((byte)0);

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Jhonny Choque");

        Mockito.when(libroRepository.findById(1L)).thenReturn(Optional.of(libro));
        Mockito.when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        boolean resultado = libroService.prestarLibroAUsuario(dto);
        assertTrue(resultado);
        assertEquals(1, libro.getEstado());
        assertEquals(usuario, libro.getUsuario());
        Mockito.verify(libroRepository).save(libro);

    }
    @Test
    void devolverLibroDebeCambiarEstadoADisponible(){
        Long id = 1L;
        Libro libro = new Libro();
        Mockito.when(libroRepository.findById(id)).thenReturn(Optional.of(libro));
        libroService.devolverLibroABiblioteca(id);
        assertEquals(0,libro.getEstado());
        Mockito.verify(libroRepository).save(libro);
    }
    @Test
    void buscarLibrosDebeRetornarListaCorrecta() {
        String palabra = "Robert";
        List<Libro> librosEsperados = List.of(new Libro(1L, "Las 48 leyes del poder", "Robert Greene", "123", (byte)1));

        // Simulamos el comportamiento del repositorio
        Mockito.when(libroRepository.filtroLibroPor(palabra)).thenReturn(librosEsperados);

        // Ejecutamos el método
        List<Libro> resultado = libroService.buscarLibrosPorTituloAutorIsbn2(palabra);

        // Comprobamos que el resultado sea el esperado
        assertEquals(1, resultado.size());
        assertEquals("Las 48 leyes del poder", resultado.get(0).getTitulo());

        // Verifica que el repositorio fue llamado
        Mockito.verify(libroRepository).filtroLibroPor(palabra);
    }

}
