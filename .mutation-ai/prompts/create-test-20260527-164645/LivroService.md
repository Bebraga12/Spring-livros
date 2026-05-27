Generate a JUnit 5 + Mockito unit test file for the Java class below.
Output ONLY raw Java code. No markdown, no explanation, no code fences.

TEST CLASS: LivroServiceTest
PACKAGE: com.livro.crud.livros_crud.service

REQUIRED CLASS SKELETON — copy this EXACTLY, then fill in the @Test methods:
@ExtendWith(MockitoExtension.class)
public class LivroServiceTest {

    @Mock
    private LivroRepository livroRepository;

    @InjectMocks
    private LivroService subject;

    // @Test methods go here
}
NOTE: field injection — Mockito injects @Mock fields into @InjectMocks via reflection.
Do NOT use @SpringBootTest or @Autowired in the test.

IMPORTS TO USE (exact FQNs from source class — do not invent others):
  java.util.List
  java.util.Optional
  com.livro.crud.livros_crud.entity.Autor
  com.livro.crud.livros_crud.entity.Livro
  com.livro.crud.livros_crud.repository.LivroRepository

METHODS TO TEST (call subject.<method> with EXACTLY the parameter types listed — do not invent overloads):
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
- CRITICAL — assert ONLY what the code actually does: read the method body carefully.
  If the method always returns the same value (e.g. `return new ResponseEntity<>(x, HttpStatus.OK)`),
  assert exactly that value. Do NOT assert status codes or return values that the code cannot produce.
- cover each branch (if/else, orElse, ternary, guard clause)
- mock Optional.of(...) for found and Optional.empty() for not-found paths
- when a method calls orElse(null) and then uses the result without null check,
  the not-found branch throws NullPointerException — use assertThrows(NullPointerException.class, ...) for it
- use verify() on mocks when the observable result is a collaborator call
- CRITICAL — @BeforeEach Mockito stubs: With @ExtendWith(MockitoExtension.class) strict mode, EVERY stub set up in @BeforeEach
  MUST be used in EVERY @Test method. If a stub is only needed by one test, put it INSIDE that @Test method, not in @BeforeEach.
  Unused @BeforeEach stubs cause UnnecessaryStubbing and fail every test that doesn't use them.
  PREFERRED: make each @Test method fully self-contained with its own stubs.
- CRITICAL — deep stubs in @BeforeEach: NEVER chain mock calls inside when() — `when(mock.getX().getY()).thenReturn(v)` calls
  `mock.getX()` which returns null, then `null.getY()` throws NullPointerException. Instead:
  declare a mock for the intermediate type: `SomeType x = mock(SomeType.class); when(mock.getX()).thenReturn(x); when(x.getY()).thenReturn(v);`
  OR annotate the mock with `@Mock(answer = Answers.RETURNS_DEEP_STUBS)` and import `org.mockito.Answers`.
- CRITICAL — only test PUBLIC methods: NEVER call private or protected methods directly
  from the test class. If a method is private/protected it cannot be called from outside
  the class and any such call will cause a compilation error.
- CRITICAL — exception stubs: use `new RuntimeException()` (unchecked) in thenThrow/doThrow
  UNLESS the mocked method's signature explicitly declares `throws SomeCheckedException`.
  Throwing a checked exception from a method that doesn't declare it causes a MockitoException.
- CRITICAL — argument matchers: if the method body creates a NEW object internally
  (e.g. `Autor autor = new Autor(); autor.setId(idAutor); repo.findByAutor(autor);`)
  you CANNOT match that instance in the test. Use `any(Autor.class)` in both
  `when(repo.findByAutor(any(Autor.class))).thenReturn(...)` and
  `verify(repo).findByAutor(any(Autor.class))`. Never create a matching instance
  to stub an internally-created object — it will always fail without equals().
- CRITICAL — test data: use the no-arg constructor (`new Type()`) to create test objects,
  then use setters. Do NOT invent multi-arg constructors; they only exist if @AllArgsConstructor
  is visible in the source. Wrong constructors cause compilation errors.
- CRITICAL — non-null fields: when a method body does `obj.setX(param.getX())`, the test param
  MUST have X set to a real non-null value before the call (e.g. `param.setX("value")` for String,
  `param.setX(2023)` for int). Forgetting to set a String field leaves it null, which causes
  NullPointerException in setters that enforce `@Nonnull` constraints.
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