Generate a JUnit 5 + Mockito unit test file for the Java class below.
Output ONLY raw Java code. No markdown, no explanation, no code fences.

TEST CLASS: AutorServiceTest
PACKAGE: com.livro.crud.livros_crud.service

REQUIRED CLASS SKELETON — copy this EXACTLY, then fill in the @Test methods:
@ExtendWith(MockitoExtension.class)
public class AutorServiceTest {

    @Mock
    private AutorRepository autorRepository;

    @InjectMocks
    private AutorService subject;

    // @Test methods go here
}
NOTE: field injection — Mockito injects @Mock fields into @InjectMocks via reflection.
Do NOT use @SpringBootTest or @Autowired in the test.

IMPORTS TO USE (exact FQNs from source class — do not invent others):
  java.util.List
  java.util.Optional
  com.livro.crud.livros_crud.entity.Autor
  com.livro.crud.livros_crud.repository.AutorRepository

METHODS TO TEST (call subject.<method> with EXACTLY the parameter types listed — do not invent overloads):
1. findAllAutors() -> List<Autor>
   body:
   {
       return autorRepository.findAll();
   }
2. findByIdAutor(Long id) -> Autor
   body:
   {
       Optional<Autor> autor = autorRepository.findById(id);
       return autor.orElse(null);
   }
3. deleteAutor(long id) -> void
   body:
   {
       this.autorRepository.deleteById(id);
   }
4. saveAutor(Autor autor) -> Autor
   body:
   {
       return this.autorRepository.save(autor);
   }
5. updateAutor(long id, Autor autor) -> Autor
   body:
   {
       Autor autorExistente = this.findByIdAutor(id);
       autorExistente.setNome(autor.getNome());
       autorExistente.setIdade(autor.getIdade());
       return this.autorRepository.save(autorExistente);
   }

COVERAGE RULES per method:
- assert the actual return value (not just non-null)
- cover each branch (if/else, orElse, ternary, guard clause)
- mock Optional.of(...) for found and Optional.empty() for not-found paths
- when a method calls orElse(null) and then uses the result without null check,
  the not-found branch throws NullPointerException — use assertThrows(NullPointerException.class, ...) for it
- use verify() on mocks when the observable result is a collaborator call
- CRITICAL — argument matchers: if the method body creates a NEW object internally
  (e.g. `Autor autor = new Autor(); autor.setId(idAutor); repo.findByAutor(autor);`)
  you CANNOT match that instance in the test. Use `any(Autor.class)` in both
  `when(repo.findByAutor(any(Autor.class))).thenReturn(...)` and
  `verify(repo).findByAutor(any(Autor.class))`. Never create a matching instance
  to stub an internally-created object — it will always fail without equals().
- CRITICAL — test data: use the no-arg constructor (`new Type()`) to create test objects,
  then use setters. Do NOT invent multi-arg constructors; they only exist if @AllArgsConstructor
  is visible in the source. Wrong constructors cause compilation errors.
- use descriptive test method names

SOURCE CLASS:
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