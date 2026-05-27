Generate a JUnit 5 + Mockito unit test file for the Java class below.
Output ONLY raw Java code. No markdown, no explanation, no code fences.

TEST CLASS: LivroControllerTest
PACKAGE: com.livro.crud.livros_crud.controller

REQUIRED CLASS SKELETON — copy this EXACTLY, then fill in the @Test methods:
@ExtendWith(MockitoExtension.class)
public class LivroControllerTest {

    @Mock
    private LivroService livroService;

    @InjectMocks
    private LivroController subject;

    // @Test methods go here
}
NOTE: field injection — Mockito injects @Mock fields into @InjectMocks via reflection.
Do NOT use @SpringBootTest or @Autowired in the test.

IMPORTS TO USE (exact FQNs from source class — do not invent others):
  java.util.List
  org.springframework.http.HttpStatus
  org.springframework.http.ResponseEntity
  com.livro.crud.livros_crud.entity.Livro
  com.livro.crud.livros_crud.service.LivroService

METHODS TO TEST (call subject.<method> with EXACTLY the parameter types listed — do not invent overloads):
1. saveLivro(Livro livro) -> ResponseEntity<Livro>
   body:
   {
       try {
           Livro save = livroService.saveLivro(livro);
           return new ResponseEntity<>(save, HttpStatus.CREATED);
       } catch (Exception e) {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
   }
2. findAll() -> ResponseEntity<List<Livro>>
   body:
   {
       try {
           List<Livro> findAll = this.livroService.findAllLivros();
           return new ResponseEntity<>(findAll, HttpStatus.OK);
       } catch (Exception e) {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
   }
3. findById(Long id) -> ResponseEntity<Livro>
   body:
   {
       try {
           Livro findbyid = this.livroService.findByIdLivro(id);
           if (findbyid == null) {
               return new ResponseEntity<>(HttpStatus.NOT_FOUND);
           }
           return new ResponseEntity<>(findbyid, HttpStatus.OK);
       } catch (Exception e) {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
   }
4. update(Long id, Livro livro) -> ResponseEntity<Livro>
   body:
   {
       try {
           Livro update = this.livroService.update(id, livro);
           return new ResponseEntity<>(update, HttpStatus.OK);
       } catch (Exception e) {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
   }
5. delete(Long id) -> ResponseEntity
   body:
   {
       try {
           this.livroService.delete(id);
           return new ResponseEntity<>(HttpStatus.OK);
       } catch (Exception e) {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
   }
6. findByTitulo(String titulo) -> ResponseEntity<List<Livro>>
   body:
   {
       try {
           List<Livro> lista = this.livroService.findByTitulo(titulo);
           return new ResponseEntity<>(lista, HttpStatus.OK);
       } catch (Exception e) {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
   }
7. findByAno(int ano) -> ResponseEntity<List<Livro>>
   body:
   {
       try {
           List<Livro> lista = this.livroService.findByAno(ano);
           return new ResponseEntity<>(lista, HttpStatus.OK);
       } catch (Exception e) {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
   }
8. findByAutor(long idAutor) -> ResponseEntity<List<Livro>>
   body:
   {
       try {
           List<Livro> lista = this.livroService.findByAutor(idAutor);
           return new ResponseEntity<>(lista, HttpStatus.OK);
       } catch (Exception e) {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
   }
9. findByAnoMaiorQue(int ano) -> ResponseEntity<List<Livro>>
   body:
   {
       try {
           List<Livro> lista = this.livroService.findByAnoMaiorQue(ano);
           return new ResponseEntity<>(lista, HttpStatus.OK);
       } catch (Exception e) {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
   }

COVERAGE RULES per method:
- assert the actual return value (not just non-null)
- CRITICAL — assert ONLY what the code actually does: read the method body carefully.
  If the method always returns the same value (e.g. `return new ResponseEntity<>(x, HttpStatus.OK)`),
  assert exactly that value. Do NOT assert status codes or return values that the code cannot produce.
- cover each branch (if/else, orElse, ternary, guard clause)
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
package com.livro.crud.livros_crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.livro.crud.livros_crud.entity.Livro;
import com.livro.crud.livros_crud.service.LivroService;

@RestController
@RequestMapping("/livro")
@CrossOrigin("*")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @PostMapping("/save")
    public ResponseEntity<Livro> saveLivro(@RequestBody Livro livro) {
        try {
            Livro save = livroService.saveLivro(livro);
            return new ResponseEntity<>(save, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Livro>> findAll() {
        try {
            List<Livro> findAll = this.livroService.findAllLivros();
            return new ResponseEntity<>(findAll, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Livro> findById(@PathVariable Long id) {
        try {
            Livro findbyid = this.livroService.findByIdLivro(id);
            if (findbyid == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(findbyid, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Livro> update(@PathVariable Long id, @RequestBody Livro livro) {
        try {
            Livro update = this.livroService.update(id, livro);
            return new ResponseEntity<>(update, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        try {
            this.livroService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByTitulo")
    public ResponseEntity<List<Livro>> findByTitulo(@RequestParam String titulo) {
        try {
            List<Livro> lista = this.livroService.findByTitulo(titulo);
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByAno")
    public ResponseEntity<List<Livro>> findByAno(@RequestParam int ano) {
        try {
            List<Livro> lista = this.livroService.findByAno(ano);
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByAutor")
    public ResponseEntity<List<Livro>> findByAutor(@RequestParam long idAutor) {
        try {
            List<Livro> lista = this.livroService.findByAutor(idAutor);
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByAnoMaiorQue")
    public ResponseEntity<List<Livro>> findByAnoMaiorQue(@RequestParam int ano) {
        try {
            List<Livro> lista = this.livroService.findByAnoMaiorQue(ano);
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}