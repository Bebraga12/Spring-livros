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

COVERAGE RULES per method:
- assert the actual return value (not just non-null)
- CRITICAL — assert ONLY what the code actually does: read the method body carefully.
  If the method always returns the same value (e.g. `return new ResponseEntity<>(x, HttpStatus.OK)`),
  assert exactly that value. Do NOT assert status codes or return values that the code cannot produce.
- cover each branch (if/else, orElse, ternary, guard clause)
- use verify() on mocks when the observable result is a collaborator call
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