Generate a JUnit 5 + Mockito unit test file for the Java class below.
Output ONLY raw Java code. No markdown, no explanation, no code fences.

TEST CLASS: UsuarioTest
PACKAGE: com.livro.crud.livros_crud.auth

REQUIRED CLASS SKELETON — copy this EXACTLY, then fill in the @Test methods:
@ExtendWith(MockitoExtension.class)
public class UsuarioTest {

    @Mock
    private Long id;
    @Mock
    private String username;
    @Mock
    private String password;
    @Mock
    private String role;

    @InjectMocks
    private Usuario subject;

    // @Test methods go here
}
NOTE: field injection — Mockito injects @Mock fields into @InjectMocks via reflection.
Do NOT use @SpringBootTest or @Autowired in the test.

IMPORTS TO USE (exact FQNs from source class — do not invent others):
  java.util.ArrayList
  java.util.Collection
  java.util.List
  org.springframework.security.core.GrantedAuthority
  org.springframework.security.core.authority.SimpleGrantedAuthority
  org.springframework.security.core.userdetails.UserDetails
  com.fasterxml.jackson.annotation.JsonIgnore
  lombok.Getter
  lombok.Setter

METHODS TO TEST (call subject.<method> with EXACTLY the parameter types listed — do not invent overloads):
1. getAuthorities() -> Collection<? extends GrantedAuthority>
   body:
   {
       List<GrantedAuthority> authorities = new ArrayList<>();
       authorities.add(new SimpleGrantedAuthority(this.role));
       return authorities;
   }
2. getPassword() -> String
   body:
   {
       return password;
   }
3. getUsername() -> String
   body:
   {
       return username;
   }

COVERAGE RULES per method:
- assert the actual return value (not just non-null)
- cover each branch (if/else, orElse, ternary, guard clause)
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
package com.livro.crud.livros_crud.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Usuario implements UserDetails{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String username;
	private String password;
	private String role;

	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(this.role));
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

}