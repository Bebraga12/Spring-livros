Generate a JUnit 5 + Mockito unit test file for the Java class below.
Output ONLY raw Java code. No markdown, no explanation, no code fences.

TEST CLASS: LivroServiceTest
PACKAGE: com.livro.crud.livros_crud.service

DEPENDENCIES (declare each as @Mock):
  @Mock LivroRepository livroRepository
  @InjectMocks LivroService subject;
NOTE: this class has no explicit constructor — uses @Autowired field injection.
Mockito will inject @Mock fields via reflection into @InjectMocks. Do NOT use @SpringBootTest or @Autowired in the test.

IMPORTS TO USE (exact FQNs from source class — do not invent others):
  java.util.List
  java.util.Optional
  com.livro.crud.livros_crud.entity.Autor
  com.livro.crud.livros_crud.entity.Livro
  com.livro.crud.livros_crud.repository.LivroRepository

METHODS TO TEST:
1. saveLivro(Livro livro) -> Livro
   body:
   {
       return livroRepository.save(livro);
   }
2. findAllLivros() -> List<Livro>
   body:
   {
       return livroRepository.findAll();
   }
3. findByIdLivro(Long id) -> Livro
   body:
   {
       Optional<Livro> livro = livroRepository.findById(id);
       return livro.orElse(null);
   }
4. update(Long id, Livro livro) -> Livro
   body:
   {
       Livro livroExistente = this.findByIdLivro(id);
       livroExistente.setTitulo(livro.getTitulo());
       livroExistente.setAno(livro.getAno());
       return livroRepository.save(livroExistente);
   }
5. delete(Long id) -> void
   body:
   {
       this.livroRepository.deleteById(id);
   }
6. findByTitulo(String titulo) -> List<Livro>
   body:
   {
       return this.livroRepository.findByTitulo(titulo);
   }
7. findByAno(int ano) -> List<Livro>
   body:
   {
       return this.livroRepository.findByAno(ano);
   }
8. findByAutor(long idAutor) -> List<Livro>
   body:
   {
       Autor autor = new Autor();
       autor.setId(idAutor);
       return this.livroRepository.findByAutor(autor);
   }
9. findByAnoMaiorQue(int ano) -> List<Livro>
   body:
   {
       return this.livroRepository.findByAnoMaiorQue(ano);
   }

COVERAGE RULES per method:
- assert the actual return value (not just non-null)
- cover each branch (if/else, orElse, ternary, guard clause)
- mock Optional.of(...) for found and Optional.empty() for not-found paths
- use verify() on mocks when the observable result is a collaborator call
- use descriptive test method names

SOURCE CLASS:
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