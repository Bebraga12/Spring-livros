package com.livro.crud.livros_crud.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.livro.crud.livros_crud.entity.Livro;
import com.livro.crud.livros_crud.service.LivroService;
import java.util.Optional;
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
public class LivroControllerTest {

    @Mock
    private LivroService livroService;

    @InjectMocks
    private LivroController subject;

    @BeforeEach
    public void setUp() {
        // Common stubs can be set up here if needed
    }

    @Test
    public void testSaveLivro_Success() throws Exception {
        Livro livro = new Livro();
        livro.setId(1L);
        livro.setTitulo("Test Title");
        when(livroService.saveLivro(any(Livro.class))).thenReturn(livro);
        ResponseEntity<Livro> response = subject.saveLivro(livro);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(livro, response.getBody());
        verify(livroService).saveLivro(livro);
    }

    @Test
    public void testSaveLivro_Exception() throws Exception {
        Livro livro = new Livro();
        when(livroService.saveLivro(any(Livro.class))).thenThrow(new RuntimeException());
        ResponseEntity<Livro> response = subject.saveLivro(livro);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testFindAll_Success() throws Exception {
        List<Livro> livros = new ArrayList<>();
        Livro livro1 = new Livro();
        livro1.setId(1L);
        livro1.setTitulo("Book 1");
        livros.add(livro1);
        when(livroService.findAllLivros()).thenReturn(livros);
        ResponseEntity<List<Livro>> response = subject.findAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(livros, response.getBody());
        verify(livroService).findAllLivros();
    }

    @Test
    public void testFindAll_Exception() throws Exception {
        when(livroService.findAllLivros()).thenThrow(new RuntimeException());
        ResponseEntity<List<Livro>> response = subject.findAll();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testFindById_Success() throws Exception {
        Livro livro = new Livro();
        livro.setId(1L);
        livro.setTitulo("Book 1");
        when(livroService.findByIdLivro(anyLong())).thenReturn(Optional.of(livro));
        ResponseEntity<Livro> response = subject.findById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(livro, response.getBody());
        verify(livroService).findByIdLivro(1L);
    }

    @Test
    public void testFindById_NotFound() throws Exception {
        when(livroService.findByIdLivro(anyLong())).thenReturn(Optional.empty());
        ResponseEntity<Livro> response = subject.findById(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testFindById_Exception() throws Exception {
        when(livroService.findByIdLivro(anyLong())).thenThrow(new RuntimeException());
        ResponseEntity<Livro> response = subject.findById(1L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testUpdate_Success() throws Exception {
        Livro livro = new Livro();
        livro.setId(1L);
        livro.setTitulo("Book 1");
        when(livroService.update(anyLong(), any(Livro.class))).thenReturn(livro);
        ResponseEntity<Livro> response = subject.update(1L, livro);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(livro, response.getBody());
        verify(livroService).update(1L, livro);
    }

    @Test
    public void testUpdate_Exception() throws Exception {
        Livro livro = new Livro();
        when(livroService.update(anyLong(), any(Livro.class))).thenThrow(new RuntimeException());
        ResponseEntity<Livro> response = subject.update(1L, livro);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testDelete_Success() throws Exception {
        doNothing().when(livroService).delete(anyLong());
        ResponseEntity<Void> response = subject.delete(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(livroService).delete(1L);
    }

    @Test
    public void testDelete_Exception() throws Exception {
        doThrow(new RuntimeException()).when(livroService).delete(anyLong());
        ResponseEntity<Void> response = subject.delete(1L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testFindByTitulo_Success() throws Exception {
        List<Livro> livros = new ArrayList<>();
        Livro livro1 = new Livro();
        livro1.setId(1L);
        livro1.setTitulo("Book 1");
        livros.add(livro1);
        when(livroService.findByTitulo(anyString())).thenReturn(livros);
        ResponseEntity<List<Livro>> response = subject.findByTitulo("Book");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(livros, response.getBody());
        verify(livroService).findByTitulo("Book");
    }

    @Test
    public void testFindByTitulo_Exception() throws Exception {
        when(livroService.findByTitulo(anyString())).thenThrow(new RuntimeException());
        ResponseEntity<List<Livro>> response = subject.findByTitulo("Book");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testFindByAno_Success() throws Exception {
        List<Livro> livros = new ArrayList<>();
        Livro livro1 = new Livro();
        livro1.setId(1L);
        livro1.setAno(2020);
        livros.add(livro1);
        when(livroService.findByAno(anyInt())).thenReturn(livros);
        ResponseEntity<List<Livro>> response = subject.findByAno(2020);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(livros, response.getBody());
        verify(livroService).findByAno(2020);
    }

    @Test
    public void testFindByAno_Exception() throws Exception {
        when(livroService.findByAno(anyInt())).thenThrow(new RuntimeException());
        ResponseEntity<List<Livro>> response = subject.findByAno(2020);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testFindByAutor_Success() throws Exception {
        List<Livro> livros = new ArrayList<>();
        Livro livro1 = new Livro();
        livro1.setId(1L);
        livro1.setAutorId(1L);
        livros.add(livro1);
        when(livroService.findByAutor(anyLong())).thenReturn(livros);
        ResponseEntity<List<Livro>> response = subject.findByAutor(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(livros, response.getBody());
        verify(livroService).findByAutor(1L);
    }

    @Test
    public void testFindByAutor_Exception() throws Exception {
        when(livroService.findByAutor(anyLong())).thenThrow(new RuntimeException());
        ResponseEntity<List<Livro>> response = subject.findByAutor(1L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testFindByAnoMaiorQue_Success() throws Exception {
        List<Livro> livros = new ArrayList<>();
        Livro livro1 = new Livro();
        livro1.setId(1L);
        livro1.setAno(2020);
        livros.add(livro1);
        when(livroService.findByAnoMaiorQue(anyInt())).thenReturn(livros);
        ResponseEntity<List<Livro>> response = subject.findByAnoMaiorQue(2019);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(livros, response.getBody());
        verify(livroService).findByAnoMaiorQue(2019);
    }

    @Test
    public void testFindByAnoMaiorQue_Exception() throws Exception {
        when(livroService.findByAnoMaiorQue(anyInt())).thenThrow(new RuntimeException());
        ResponseEntity<List<Livro>> response = subject.findByAnoMaiorQue(2019);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
