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
- CRITICAL — chained mock calls in when(): NEVER write `when(mock.getX().getY()).thenReturn(v)`.
  `mock.getX()` returns null by default, then `null.getY()` throws NullPointerException.
  ALWAYS stub each step separately:
    SomeType x = mock(SomeType.class);
    when(mock.getX()).thenReturn(x);
    when(x.getY()).thenReturn(v);
  Common examples that require this pattern:
  * MethodArgumentNotValidException: `BindingResult br = mock(BindingResult.class);`
    `when(ex.getBindingResult()).thenReturn(br);`
    `when(br.getFieldErrors()).thenReturn(List.of(fieldError));`
  * ConstraintViolation: `Path path = mock(Path.class);`
    `when(violation.getPropertyPath()).thenReturn(path);`
    `when(path.toString()).thenReturn("fieldName");`
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
- CRITICAL — collaborator return types: infer the return type of each collaborator call from the
  LOCAL VARIABLE TYPE in the method body. If the body says `Livro x = service.findById(id)`,
  then `service.findById` returns `Livro` — stub with `when(service.findById(any())).thenReturn(new Livro())`.
  If the body says `Optional<Livro> x = service.findById(id)`, it returns `Optional<Livro>`.
  Do NOT assume Optional when the variable type is the plain entity. Wrong return type causes compilation error.
- CRITICAL — entity field names: use ONLY setters that exist on the entity. Do not invent field names
  like `setAutorId()` unless that exact field appears in the source. Check the SOURCE CLASS imports and body.
  If the entity field is a foreign key stored as a Long, use `setAutor(autorObject)` or `setAutorId(id)`
  ONLY if those names actually appear in the entity source. When in doubt, use the no-arg constructor alone.
- CRITICAL — argument transformation: if the method transforms an argument before passing it to
  a collaborator (e.g. `String token = header.substring(7); service.extractUsername(token);`),
  the collaborator receives the TRANSFORMED value, not the original. The stub MUST reflect this.
  SAFE approach: use `anyString()` or `any(Type.class)` in stubs for collaborators that receive
  internally-transformed values, so the stub matches regardless of the exact transformed content.
- CRITICAL — exact method signatures: call each method with EXACTLY the parameter count and types
  shown in METHODS TO TEST. Do NOT add extra parameters or change types. A method listed as
  `extractUsername(String token)` takes ONE String — do not call it with two arguments.
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