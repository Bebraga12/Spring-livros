package com.livro.crud.livros_crud.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.livro.crud.livros_crud.entity.Autor;
import com.livro.crud.livros_crud.service.AutorService;
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
public class AutorControllerTest {

    @Mock
    private AutorService autorService;

    @InjectMocks
    private AutorController subject;

    @BeforeEach
    public void setUp() {
        // Common stubs can be set up here if needed
    }

    @Test
    public void testFindAllAutor_Success() throws Exception {
        List<Autor> mockList = new ArrayList<>();
        when(autorService.findAllAutors()).thenReturn(mockList);
        ResponseEntity<List<Autor>> response = subject.findAllAutor();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockList, response.getBody());
        verify(autorService).findAllAutors();
    }

    @Test
    public void testFindAllAutor_Exception() throws Exception {
        when(autorService.findAllAutors()).thenThrow(new RuntimeException());
        ResponseEntity<List<Autor>> response = subject.findAllAutor();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testDeleteAutor_Success() throws Exception {
        Long id = 1L;
        doNothing().when(autorService).deleteAutor(id);
        ResponseEntity<Void> response = subject.deleteAutor(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(autorService).deleteAutor(id);
    }

    @Test
    public void testDeleteAutor_Exception() throws Exception {
        Long id = 1L;
        doThrow(new RuntimeException()).when(autorService).deleteAutor(id);
        ResponseEntity<Void> response = subject.deleteAutor(id);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testSaveAutor_Success() throws Exception {
        Autor mockAutor = new Autor();
        when(autorService.saveAutor(any(Autor.class))).thenReturn(mockAutor);
        ResponseEntity<Autor> response = subject.saveAutor(mockAutor);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockAutor, response.getBody());
        verify(autorService).saveAutor(any(Autor.class));
    }

    @Test
    public void testSaveAutor_Exception() throws Exception {
        Autor mockAutor = new Autor();
        when(autorService.saveAutor(any(Autor.class))).thenThrow(new RuntimeException());
        ResponseEntity<Autor> response = subject.saveAutor(mockAutor);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testUpdate_Success() throws Exception {
        Long id = 1L;
        Autor mockAutor = new Autor();
        when(autorService.updateAutor(id, any(Autor.class))).thenReturn(mockAutor);
        ResponseEntity<Autor> response = subject.update(id, mockAutor);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockAutor, response.getBody());
        verify(autorService).updateAutor(id, any(Autor.class));
    }

    @Test
    public void testUpdate_Exception() throws Exception {
        Long id = 1L;
        Autor mockAutor = new Autor();
        when(autorService.updateAutor(id, any(Autor.class))).thenThrow(new RuntimeException());
        ResponseEntity<Autor> response = subject.update(id, mockAutor);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
