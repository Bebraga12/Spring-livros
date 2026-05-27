package com.livro.crud.livros_crud.service;

import java.util.List;
import java.util.Optional;
import com.livro.crud.livros_crud.entity.Autor;
import com.livro.crud.livros_crud.repository.AutorRepository;
import java.util.ArrayList;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.Answers;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(value = MockitoExtension.class)
public class AutorServiceTest {

    @Mock
    private AutorRepository autorRepository;

    @InjectMocks
    private AutorService subject;

    @BeforeEach
    public void setUp() {
        // No-op setup for strict mode
    }

    @Test
    public void testFindAllAutors() throws Exception {
        List<Autor> expected = List.of(new Autor(), new Autor());
        when(autorRepository.findAll()).thenReturn(expected);
        List<Autor> result = subject.findAllAutors();
        assertEquals(expected, result);
        verify(autorRepository).findAll();
    }

    @Test
    public void testFindByIdAutor_found() throws Exception {
        Long id = 1L;
        Autor expected = new Autor();
        when(autorRepository.findById(id)).thenReturn(Optional.of(expected));
        Autor result = subject.findByIdAutor(id);
        assertEquals(expected, result);
        verify(autorRepository).findById(id);
    }

    @Test
    public void testFindByIdAutor_notFound() throws Exception {
        Long id = 1L;
        when(autorRepository.findById(id)).thenReturn(Optional.empty());
        Autor result = subject.findByIdAutor(id);
        assertNull(result);
        verify(autorRepository).findById(id);
    }

    @Test
    public void testDeleteAutor() throws Exception {
        Long id = 1L;
        subject.deleteAutor(id);
        verify(autorRepository).deleteById(id);
    }

    @Test
    public void testSaveAutor() throws Exception {
        Autor autor = new Autor();
        when(autorRepository.save(autor)).thenReturn(autor);
        Autor result = subject.saveAutor(autor);
        assertEquals(autor, result);
        verify(autorRepository).save(autor);
    }

    @Test
    public void testUpdateAutor_found() throws Exception {
        Long id = 1L;
        Autor autorToUpdate = new Autor();
        when(autorRepository.findById(id)).thenReturn(Optional.of(new Autor()));
        when(autorRepository.save(any(Autor.class))).thenReturn(autorToUpdate);
        Autor result = subject.updateAutor(id, autorToUpdate);
        assertEquals(autorToUpdate, result);
        verify(autorRepository).findById(id);
        verify(autorRepository).save(any(Autor.class));
    }

    @Test
    public void testUpdateAutor_notFound() throws Exception {
        Long id = 1L;
        Autor autorToUpdate = new Autor();
        when(autorRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NullPointerException.class, () -> subject.updateAutor(id, autorToUpdate));
        verify(autorRepository).findById(id);
    }
}
