Generate a JUnit 5 + Mockito unit test file for the Java class below.
Output ONLY raw Java code. No markdown, no explanation, no code fences.

TEST CLASS: AutorRepositoryTest
PACKAGE: com.livro.crud.livros_crud.repository

IMPORTS TO USE (exact FQNs from source class — do not invent others):
  java.util.Optional
  org.springframework.data.jpa.repository.JpaRepository
  com.livro.crud.livros_crud.entity.Autor

METHODS TO TEST (call subject.<method> with EXACTLY the parameter types listed — do not invent overloads):
1. findById(Long id) -> Optional<Autor>

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
- CRITICAL — non-null fields: when a method body does `obj.setX(param.getX())`, the test param
  MUST have X set to a real non-null value before the call (e.g. `param.setX("value")` for String,
  `param.setX(2023)` for int). Forgetting to set a String field leaves it null, which causes
  NullPointerException in setters that enforce `@Nonnull` constraints.
- use descriptive test method names

SOURCE CLASS:
package com.livro.crud.livros_crud.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.livro.crud.livros_crud.entity.Autor;

public interface AutorRepository extends JpaRepository<Autor, Long>{

    public Optional<Autor> findById(Long id);

}