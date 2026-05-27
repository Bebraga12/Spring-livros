Generate a JUnit 5 + Mockito unit test file for the Java class below.
Output ONLY raw Java code. No markdown, no explanation, no code fences.

TEST CLASS: GlobalExceptionHandlerTest
PACKAGE: com.livro.crud.livros_crud.config

IMPORTS TO USE (exact FQNs from source class — do not invent others):
  java.util.HashMap
  java.util.Map
  org.springframework.http.HttpStatus
  org.springframework.http.ResponseEntity
  org.springframework.validation.FieldError
  org.springframework.web.bind.MethodArgumentNotValidException
  jakarta.validation.ConstraintViolation
  jakarta.validation.ConstraintViolationException

METHODS TO TEST (call subject.<method> with EXACTLY the parameter types listed — do not invent overloads):
1. handle01(MethodArgumentNotValidException ex) -> ResponseEntity<Map<String,String>>
   body:
   {
       Map<String, String> erros = new HashMap<>();
       for (FieldError fildError : ex.getBindingResult().getFieldErrors()) {
           erros.put(fildError.getField(), fildError.getDefaultMessage());
       }
       return new ResponseEntity<Map<String, String>>(erros, HttpStatus.BAD_REQUEST);
   }
2. handle02(ConstraintViolationException ex) -> ResponseEntity<Map<String,String>>
   body:
   {
       Map<String, String> erros = new HashMap<>();
       for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
           erros.put(violation.getPropertyPath().toString(), violation.getMessage());
       }
       return new ResponseEntity<Map<String, String>>(erros, HttpStatus.BAD_REQUEST);
   }
3. handle03(Exception ex) -> ResponseEntity<String>
   body:
   {
       ex.printStackTrace();
       return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
   }

NOTE: This is a @Configuration or @ControllerAdvice class. Special rules:
- Test ONLY the @Bean/@ExceptionHandler methods listed in METHODS TO TEST above.
- Do NOT mock Spring Security DSL builders (HttpSecurity, WebSecurityConfigurerAdapter, etc.).
- Do NOT use reflection (getDeclaredMethod, setAccessible, invoke) to inspect framework internals.
  Framework classes like DaoAuthenticationProvider have NO public getters for their internal state.
  DaoAuthenticationProvider.getUserDetailsService() and getPasswordEncoder() are PROTECTED — compilation error.
- For @Bean methods returning framework objects (AuthenticationProvider, PasswordEncoder, etc.):
  assert ONLY: `assertNotNull(result); assertTrue(result instanceof ExpectedType);`
  DO NOT cast to DaoAuthenticationProvider and call methods on it.
- For @Bean methods that accept a parameter (e.g. AuthenticationConfiguration): mock the parameter,
  stub its relevant methods, call the @Bean method on `subject`, assert the returned value is not null.
- For @ExceptionHandler(MethodArgumentNotValidException.class): stub getBindingResult() FIRST:
    org.springframework.validation.BindingResult br = mock(org.springframework.validation.BindingResult.class);
    org.springframework.validation.FieldError fe = mock(org.springframework.validation.FieldError.class);
    when(fe.getField()).thenReturn("field");
    when(fe.getDefaultMessage()).thenReturn("error");
    when(br.getFieldErrors()).thenReturn(java.util.List.of(fe));
    when(ex.getBindingResult()).thenReturn(br);
    // Then assert: body.get("field") equals "error"
- For @ExceptionHandler(ConstraintViolationException.class): stub getPropertyPath() THEN toString():
    jakarta.validation.Path path = mock(jakarta.validation.Path.class);
    when(path.toString()).thenReturn("field");
    when(violation.getPropertyPath()).thenReturn(path);
    when(violation.getMessage()).thenReturn("error");
    when(ex.getConstraintViolations()).thenReturn(java.util.Set.of(violation));
    // Then assert: body.get("field") equals "error"
  NEVER do: `when(violation.getPropertyPath().toString()).thenReturn(...)` — NPE!
  NEVER do: `when(ex.getBindingResult().getFieldErrors()).thenReturn(...)` — NPE!

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
package com.livro.crud.livros_crud.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handle01(MethodArgumentNotValidException ex) {
		Map<String, String> erros = new HashMap<>();
		for (FieldError fildError : ex.getBindingResult().getFieldErrors()) {
			erros.put(fildError.getField(), fildError.getDefaultMessage());
		}
		return new ResponseEntity<Map<String, String>>(erros, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Map<String, String>> handle02(ConstraintViolationException ex) {
		Map<String, String> erros = new HashMap<>();
		for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			erros.put(violation.getPropertyPath().toString(), violation.getMessage());
		}
		return new ResponseEntity<Map<String, String>>(erros, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handle03(Exception ex) {
		ex.printStackTrace();
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

}