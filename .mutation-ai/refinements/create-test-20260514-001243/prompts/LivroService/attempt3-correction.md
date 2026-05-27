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
- assertNull em update not found incompatível com fluxo real da implementação

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
- fullyQualifiedName: com.livro.crud.livros_crud.service.LivroService
- sourceFile: src/main/java/com/livro/crud/livros_crud/service/LivroService.java

Código fonte da classe alvo:
package com.livro.crud.livros_crud.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.livro.crud.livros_crud.entity.Autor;
import com.livro.crud.livros_crud.entity.Livro;
import com.livro.crud.livros_crud.repository.LivroRepository;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    public Livro saveLivro(Livro livro){
        return livroRepository.save(livro);
    }

    public List<Livro> findAllLivros(){
        return livroRepository.findAll();
    }

    public Livro findByIdLivro(Long id){
        Optional<Livro> livro = livroRepository.findById(id);
        return livro.orElse(null);
    }

    public Livro update(Long id, Livro livro){
        Livro livroExistente = this.findByIdLivro(id);
        livroExistente.setTitulo(livro.getTitulo());
        livroExistente.setAno(livro.getAno());
        return livroRepository.save(livroExistente);
    }

    public void delete(Long id){
        this.livroRepository.deleteById(id);
    }

    public List<Livro> findByTitulo(String titulo){
        return this.livroRepository.findByTitulo(titulo);
    }

    public List<Livro> findByAno(int ano){
        return this.livroRepository.findByAno(ano);
    }

    public List<Livro> findByAutor(long idAutor){
        Autor autor = new Autor();
        autor.setId(idAutor);
        return this.livroRepository.findByAutor(autor);
    }

    public List<Livro> findByAnoMaiorQue(int ano){
        return this.livroRepository.findByAnoMaiorQue(ano);
    }

}

Teste anterior rejeitado:
package com.livro.crud.livros_crud.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LivroServiceTest {

    @Mock
    private LivroRepository livroRepository;

    @InjectMocks
    private LivroService livroService;

    @BeforeEach
    public void setUp() {
        // Setup any initial configurations or mocks if needed
    }

    @Test
    public void testSaveLivro() {
        Livro livro = new Livro();
        when(livroRepository.save(any(Livro.class))).thenReturn(livro);

        Livro savedLivro = livroService.saveLivro(livro);
        assertEquals(livro, savedLivro);
        verify(livroRepository).save(livro);
    }

    @Test
    public void testFindAllLivros() {
        List<Livro> livros = new ArrayList<>();
        when(livroRepository.findAll()).thenReturn(livros);

        List<Livro> result = livroService.findAllLivros();
        assertEquals(livros, result);
        verify(livroRepository).findAll();
    }

    @Test
    public void testFindByIdLivro() {
        Long id = 1L;
        Livro livro = new Livro();
        when(livroRepository.findById(id)).thenReturn(Optional.of(livro));

        Livro result = livroService.findByIdLivro(id);
        assertEquals(livro, result);
        verify(livroRepository).findById(id);
    }

    @Test
    public void testFindByIdLivroNotFound() {
        Long id = 1L;
        when(livroRepository.findById(id)).thenReturn(Optional.empty());

        Livro result = livroService.findByIdLivro(id);
        assertNull(result);
        verify(livroRepository).findById(id);
    }

    @Test
    public void testUpdateLivro() {
        Long id = 1L;
        Livro livroAtualizado = new Livro();
        livroAtualizado.setId(id);
        livroAtualizado.setTitulo("Novo Titulo");
        livroAtualizado.setAno(2023);

        Livro livroExistente = new Livro();
        livroExistente.setId(id);
        when(livroRepository.findById(id)).thenReturn(Optional.of(livroExistente));

        when(livroRepository.save(any(Livro.class))).thenReturn(livroAtualizado);

        Livro result = livroService.update(id, livroAtualizado);
        assertEquals(livroAtualizado, result);
        verify(livroRepository).findById(id);
        verify(livroRepository).save(livroExistente);
    }

    @Test
    public void testUpdateLivroNotFound() {
        Long id = 1L;
        Livro livroAtualizado = new Livro();
        livroAtualizado.setId(id);
        livroAtualizado.setTitulo("Novo Titulo");
        livroAtualizado.setAno(2023);

        when(livroRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            livroService.update(id, livroAtualizado);
        });
    }

    @Test
    public void testDeleteLivro() {
        Long id = 1L;

        doNothing().when(livroRepository).deleteById(id);

        livroService.delete(id);
        verify(livroRepository).deleteById(id);
    }

    @Test
    public void testFindByTitulo() {
        String titulo = "Java";
        List<Livro> livros = new ArrayList<>();
        when(livroRepository.findByTitulo(titulo)).thenReturn(livros);

        List<Livro> result = livroService.findByTitulo(titulo);
        assertEquals(livros, result);
        verify(livroRepository).findByTitulo(titulo);
    }

    @Test
    public void testFindByAno() {
        int ano = 2023;
        List<Livro> livros = new ArrayList<>();
        when(livroRepository.findByAno(ano)).thenReturn(livros);

        List<Livro> result = livroService.findByAno(ano);
        assertEquals(livros, result);
        verify(livroRepository).findByAno(ano);
    }

    @Test
    public void testFindByAutor() {
        long idAutor = 1L;
        Autor autor = new Autor();
        autor.setId(idAutor);
        List<Livro> livros = new ArrayList<>();
        when(livroRepository.findByAutor(autor)).thenReturn(livros);

        List<Livro> result = livroService.findByAutor(idAutor);
        assertEquals(livros, result);
        verify(livroRepository).findByAutor(autor);
    }

    @Test
    public void testFindByAnoMaiorQue() {
        int ano = 2023;
        List<Livro> livros = new ArrayList<>();
        when(livroRepository.findByAnoMaiorQue(ano)).thenReturn(livros);

        List<Livro> result = livroService.findByAnoMaiorQue(ano);
        assertEquals(livros, result);
        verify(livroRepository).findByAnoMaiorQue(ano);
    }
}