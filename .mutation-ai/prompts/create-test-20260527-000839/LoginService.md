Generate a JUnit 5 + Mockito unit test file for the Java class below.
Output ONLY raw Java code. No markdown, no explanation, no code fences.

TEST CLASS: LoginServiceTest
PACKAGE: com.livro.crud.livros_crud.auth

DEPENDENCIES (declare each as @Mock):
  @Mock LoginRepository repository
  @Mock JwtServiceGenerator jwtService
  @Mock AuthenticationManager authenticationManager
  @InjectMocks LoginService subject;
NOTE: this class has no explicit constructor — uses @Autowired field injection.
Mockito will inject @Mock fields via reflection into @InjectMocks. Do NOT use @SpringBootTest or @Autowired in the test.

IMPORTS TO USE (exact FQNs from source class — do not invent others):
  org.springframework.security.authentication.AuthenticationManager
  org.springframework.security.authentication.UsernamePasswordAuthenticationToken
  com.livro.crud.livros_crud.config.JwtServiceGenerator

METHODS TO TEST:
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