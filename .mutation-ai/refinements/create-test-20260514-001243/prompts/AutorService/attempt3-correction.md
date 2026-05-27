Você está corrigindo um teste unitário Java que falhou na validação.

Objetivo:
Corrigir o teste abaixo para que ele fique coerente com o código-fonte real, estruturalmente completo e compilável no contexto do projeto.

Tentativa atual: 3 de 3

Regras obrigatórias de saída:
- responda com apenas código Java puro
- não inclua markdown
- não inclua explicações
- retorne um único arquivo Java completo e compilável
- preserve package, imports e nome da classe de teste

Motivos da rejeição:
- teste assume retorno incompatível com método void

Instruções de correção:
- corrija comportamentos incompatíveis com a implementação real
- corrija imports ausentes e static imports ausentes
- não use símbolos sem import correspondente, exceto classes do mesmo package ou de java.lang
- se o teste não compilou, priorize corrigir estrutura do arquivo, imports e símbolos não resolvidos
- se o fluxo real indicar exceção, use assertThrows(...)
- para métodos void, não capture retorno; valide com verify(...)
- quando houver delegação para dependências, valide interações relevantes com verify(...)
- não use assertNull quando o fluxo real não retornar null explicitamente

Classe alvo:
- fullyQualifiedName: com.livro.crud.livros_crud.service.AutorService
- sourceFile: src/main/java/com/livro/crud/livros_crud/service/AutorService.java

Código fonte da classe alvo:
package com.livro.crud.livros_crud.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.livro.crud.livros_crud.entity.Autor;
import com.livro.crud.livros_crud.repository.AutorRepository;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    public List<Autor> findAllAutors() {
        return autorRepository.findAll();
    }

    public Autor findByIdAutor(Long id) {
        Optional<Autor> autor = autorRepository.findById(id);
        return autor.orElse(null);
    }

    public void deleteAutor(long id) {
        this.autorRepository.deleteById(id);
    }

    public Autor saveAutor(Autor autor) {
        return this.autorRepository.save(autor);
    }

    public Autor updateAutor(long id, Autor autor) {
        Autor autorExistente = this.findByIdAutor(id);
        autorExistente.setNome(autor.getNome());
        autorExistente.setIdade(autor.getIdade());
        return this.autorRepository.save(autorExistente);

    }
}

Teste anterior rejeitado:
package com.livro.crud.livros_crud.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AutorServiceTest {

    @Mock
    private AutorRepository autorRepository;

    @InjectMocks
    private AutorService autorService;

    @BeforeEach
    public void setUp() {
        // Setup any initial configurations or mocks if needed
    }

    @Test
    public void testFindAllAutors() {
        List<Autor> autores = Arrays.asList(new Autor(), new Autor());
        when(autorRepository.findAll()).thenReturn(autores);

        List<Autor> result = autorService.findAllAutors();

        assertEquals(2, result.size());
        verify(autorRepository).findAll();
    }

    @Test
    public void testFindByIdAutor_WhenIdExists() {
        Long id = 1L;
        Autor autor = new Autor();
        when(autorRepository.findById(id)).thenReturn(Optional.of(autor));

        Autor result = autorService.findByIdAutor(id);

        assertNotNull(result);
        assertEquals(autor, result);
        verify(autorRepository).findById(id);
    }

    @Test
    public void testFindByIdAutor_WhenIdDoesNotExist() {
        Long id = 1L;
        when(autorRepository.findById(id)).thenReturn(Optional.empty());

        Autor result = autorService.findByIdAutor(id);

        assertNull(result);
        verify(autorRepository).findById(id);
    }

    @Test
    public void testDeleteAutor() {
        Long id = 1L;

        autorService.deleteAutor(id);

        verify(autorRepository).deleteById(id);
    }

    @Test
    public void testSaveAutor() {
        Autor autor = new Autor();
        when(autorRepository.save(autor)).thenReturn(autor);

        Autor result = autorService.saveAutor(autor);

        assertNotNull(result);
        assertEquals(autor, result);
        verify(autorRepository).save(autor);
    }

    @Test
    public void testUpdateAutor_WhenIdExists() {
        Long id = 1L;
        Autor autorToUpdate = new Autor();
        when(autorRepository.findById(id)).thenReturn(Optional.of(new Autor()));
        when(autorRepository.save(any(Autor.class))).thenReturn(autorToUpdate);

        Autor result = autorService.updateAutor(id, autorToUpdate);

        assertNotNull(result);
        assertEquals(autorToUpdate, result);
        verify(autorRepository).findById(id);
        verify(autorRepository).save(any(Autor.class));
    }

    @Test
    public void testUpdateAutor_WhenIdDoesNotExist() {
        Long id = 1L;
        Autor autorToUpdate = new Autor();
        when(autorRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> autorService.updateAutor(id, autorToUpdate));
    }
}