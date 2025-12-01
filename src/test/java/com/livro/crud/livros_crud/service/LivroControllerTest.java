package com.livro.crud.livros_crud.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.livro.crud.livros_crud.controller.LivroController;
import com.livro.crud.livros_crud.entity.Autor;
import com.livro.crud.livros_crud.entity.Editora;
import com.livro.crud.livros_crud.entity.Livro;
import com.livro.crud.livros_crud.entity.Proprietario;
import com.livro.crud.livros_crud.repository.LivroRepository;

@SpringBootTest
public class LivroControllerTest {

    @Autowired
    LivroController livroController;

    @MockitoBean
    LivroRepository livroRepository;

    @BeforeEach
    void setUp() {
        Proprietario proprietario1 = new Proprietario(1L, "João Silva", 35);
        Proprietario proprietario2 = new Proprietario(2L, "Maria Santos", 28);
        Proprietario proprietario3 = new Proprietario(3L, "Pedro Souza", 40);

        Autor autor1 = new Autor(1L, "Machado de Assis", 65, new ArrayList<>());
        Autor autor2 = new Autor(2L, "Clarice Lispector", 57, new ArrayList<>());

        Editora editora1 = new Editora(1L, "Editora ABC", "Rua X, 123", new ArrayList<>());
        Editora editora2 = new Editora(2L, "Editora ZYX", "Av. Y, 456", new ArrayList<>());

        Livro livro1 = new Livro(
                1L,
                "Dom Casmurro",
                1899,
                autor1,
                editora1,
                Arrays.asList(proprietario1, proprietario2));
        Livro livro2 = new Livro(
                2L,
                "Memórias Póstumas de Brás Cubas",
                1881,
                autor1,
                editora1,
                Arrays.asList(proprietario3));
        Livro livro3 = new Livro(
                3L,
                "A Hora da Estrela",
                1977,
                autor2,
                editora2,
                Arrays.asList(proprietario1, proprietario3));


        List<Livro> lista = Arrays.asList(livro1, livro2, livro3);

        when(livroRepository.findByAutor(autor1)).thenReturn(lista);
        when(livroRepository.findById(1L)).thenReturn(java.util.Optional.of(livro1));

    }


    @Test
    void cenario01() {
        ResponseEntity<List<Livro>> retorno = this.livroController.findByAutor(1L);

        assertEquals(HttpStatus.OK, retorno.getStatusCode());
        //assertEquals(3, retorno.getBody().size());
    }

    @Disabled
    @Test
    void findById() {
        ResponseEntity<Livro> retorno = this.livroController.findById(1L);

        assertEquals(HttpStatus.OK, retorno.getStatusCode());
        assertEquals(1L, retorno.getBody().getId());
    }

}
