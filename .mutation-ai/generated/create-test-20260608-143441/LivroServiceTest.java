package com.livro.crud.livros_crud.service;

import java.util.List;
import java.util.Optional;
import com.livro.crud.livros_crud.entity.Autor;
import com.livro.crud.livros_crud.entity.Livro;
import com.livro.crud.livros_crud.repository.LivroRepository;
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
public class LivroServiceTest {

    @Mock
    private LivroRepository livroRepository;

    @InjectMocks
    private LivroService subject;

    @BeforeEach
    public void setUp() {
        // Common stubs can be set up here if needed
    }

    @Test
    public void testSaveLivro() throws Exception {
        Livro livro = new Livro();
        when(livroRepository.save(livro)).thenReturn(livro);
        Livro result = subject.saveLivro(livro);
        assertEquals(livro, result);
        verify(livroRepository).save(livro);
    }

    @Test
    public void testFindAllLivros() throws Exception {
        List<Livro> livros = List.of(new Livro(), new Livro());
        when(livroRepository.findAll()).thenReturn(livros);
        List<Livro> result = subject.findAllLivros();
        assertEquals(livros, result);
        verify(livroRepository).findAll();
    }

    @Test
    public void testFindByIdLivroFound() throws Exception {
        Long id = 1L;
        Livro livro = new Livro();
        when(livroRepository.findById(id)).thenReturn(Optional.of(livro));
        Livro result = subject.findByIdLivro(id);
        assertEquals(livro, result);
        verify(livroRepository).findById(id);
    }

    @Test
    public void testFindByIdLivroNotFound() throws Exception {
        Long id = 1L;
        when(livroRepository.findById(id)).thenReturn(Optional.empty());
        Livro result = subject.findByIdLivro(id);
        assertNull(result);
        verify(livroRepository).findById(id);
    }

    @Test
    public void testUpdateFound() throws Exception {
        Long id = 1L;
        Livro livroAtualizado = new Livro();
        when(livroRepository.findById(id)).thenReturn(Optional.of(new Livro()));
        when(livroRepository.save(any(Livro.class))).thenReturn(livroAtualizado);
        Livro result = subject.update(id, livroAtualizado);
        assertEquals(livroAtualizado, result);
        verify(livroRepository).findById(id);
        verify(livroRepository).save(any(Livro.class));
    }

    @Test
    public void testUpdateNotFound() throws Exception {
        Long id = 1L;
        Livro livroAtualizado = new Livro();
        when(livroRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NullPointerException.class, () -> subject.update(id, livroAtualizado));
        verify(livroRepository).findById(id);
    }

    @Test
    public void testDelete() throws Exception {
        Long id = 1L;
        doNothing().when(livroRepository).deleteById(id);
        subject.delete(id);
        verify(livroRepository).deleteById(id);
    }

    @Test
    public void testFindByTitulo() throws Exception {
        String titulo = "Java";
        List<Livro> livros = List.of(new Livro());
        when(livroRepository.findByTitulo(titulo)).thenReturn(livros);
        List<Livro> result = subject.findByTitulo(titulo);
        assertEquals(livros, result);
        verify(livroRepository).findByTitulo(titulo);
    }

    @Test
    public void testFindByAno() throws Exception {
        int ano = 2023;
        List<Livro> livros = List.of(new Livro());
        when(livroRepository.findByAno(ano)).thenReturn(livros);
        List<Livro> result = subject.findByAno(ano);
        assertEquals(livros, result);
        verify(livroRepository).findByAno(ano);
    }

    @Test
    public void testFindByAutor() throws Exception {
        long idAutor = 1L;
        Autor autor = new Autor();
        autor.setId(idAutor);
        List<Livro> livros = List.of(new Livro());
        when(livroRepository.findByAutor(any(Autor.class))).thenReturn(livros);
        List<Livro> result = subject.findByAutor(idAutor);
        assertEquals(livros, result);
        verify(livroRepository).findByAutor(any(Autor.class));
    }

    @Test
    public void testFindByAnoMaiorQue() throws Exception {
        int ano = 2023;
        List<Livro> livros = List.of(new Livro());
        when(livroRepository.findByAnoMaiorQue(ano)).thenReturn(livros);
        List<Livro> result = subject.findByAnoMaiorQue(ano);
        assertEquals(livros, result);
        verify(livroRepository).findByAnoMaiorQue(ano);
    }
}
