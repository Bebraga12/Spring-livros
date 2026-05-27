package com.livro.crud.livros_crud.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.livro.crud.livros_crud.entity.Livro;
import com.livro.crud.livros_crud.service.LivroService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LivroControllerTest {

    @Mock
    private LivroService livroService;

    @InjectMocks
    private LivroController subject;

    @Test
    void deveInstanciarSubjeto() {
        assertNotNull(subject);
    }
}
