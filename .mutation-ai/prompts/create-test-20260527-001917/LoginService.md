Generate a JUnit 5 + Mockito unit test file for the Java class below.
Output ONLY raw Java code. No markdown, no explanation, no code fences.

TEST CLASS: LoginServiceTest
PACKAGE: com.livro.crud.livros_crud.auth

REQUIRED CLASS SKELETON — copy this EXACTLY, then fill in the @Test methods:
@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {

    @Mock
    private LoginRepository repository;
    @Mock
    private JwtServiceGenerator jwtService;
    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private LoginService subject;

    // @Test methods go here
}
NOTE: field injection — Mockito injects @Mock fields into @InjectMocks via reflection.
Do NOT use @SpringBootTest or @Autowired in the test.

IMPORTS TO USE (exact FQNs from source class — do not invent others):
  org.springframework.security.authentication.AuthenticationManager
  org.springframework.security.authentication.UsernamePasswordAuthenticationToken
  com.livro.crud.livros_crud.config.JwtServiceGenerator

METHODS TO TEST (call subject.<method> with EXACTLY the parameter types listed — do not invent overloads):
1. logar(Login login) -> String
   body:
   {
       String token = this.gerarToken(login);
       return token;
   }
2. gerarToken(Login login) -> String
   body:
   {
       authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
       Usuario user = repository.findByUsername(login.getUsername()).get();
       String jwtToken = jwtService.generateToken(user);
       return jwtToken;
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
- use descriptive test method names

SOURCE CLASS:
package com.livro.crud.livros_crud.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.livro.crud.livros_crud.config.JwtServiceGenerator;

@Service
public class LoginService {

	@Autowired
	private LoginRepository repository;
	@Autowired
	private JwtServiceGenerator jwtService;
	@Autowired
	private AuthenticationManager authenticationManager;

	public String logar(Login login) {

		String token = this.gerarToken(login);
		return token;

	}

	public String gerarToken(Login login) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						login.getUsername(),
						login.getPassword()
						)
				);
		Usuario user = repository.findByUsername(login.getUsername()).get();
		String jwtToken = jwtService.generateToken(user);
		return jwtToken;
	}

}