package com.livro.crud.livros_crud.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.livro.crud.livros_crud.entity.Livro;

@SpringBootTest
public class LivroServiceTest {
    
    @Autowired
    LivroService livroService;

    @Test
    void cenario01(){
        Livro livro = livroService.findByIdLivro( 1L);
        livro.setAno(1500);
        
        int retorno = livro.getAno();
        assertEquals(1500,retorno );
    }
}
