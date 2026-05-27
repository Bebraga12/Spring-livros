Generate a JUnit 5 + Mockito unit test file for the Java class below.
Output ONLY raw Java code. No markdown, no explanation, no code fences.

TEST CLASS: SecurityManagerTest
PACKAGE: com.livro.crud.livros_crud.config

REQUIRED CLASS SKELETON — copy this EXACTLY, then fill in the @Test methods:
@ExtendWith(MockitoExtension.class)
public class SecurityManagerTest {

    @Mock
    private LoginRepository loginRepository;

    @InjectMocks
    private SecurityManager subject;

    // @Test methods go here
}
NOTE: field injection — Mockito injects @Mock fields into @InjectMocks via reflection.
Do NOT use @SpringBootTest or @Autowired in the test.

IMPORTS TO USE (exact FQNs from source class — do not invent others):
  org.springframework.context.annotation.Bean
  org.springframework.context.annotation.Configuration
  org.springframework.security.authentication.AuthenticationManager
  org.springframework.security.authentication.AuthenticationProvider
  org.springframework.security.authentication.dao.DaoAuthenticationProvider
  org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
  org.springframework.security.core.userdetails.UserDetailsService
  org.springframework.security.core.userdetails.UsernameNotFoundException
  org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
  org.springframework.security.crypto.password.PasswordEncoder
  com.livro.crud.livros_crud.auth.LoginRepository

METHODS TO TEST (call subject.<method> with EXACTLY the parameter types listed — do not invent overloads):
1. passwordEncoder() -> PasswordEncoder
   body:
   {
       return new BCryptPasswordEncoder();
   }
2. authenticationProvider() -> AuthenticationProvider
   body:
   {
       DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
       authProvider.setUserDetailsService(userDetailsService());
       authProvider.setPasswordEncoder(passwordEncoder());
       return authProvider;
   }
3. authenticationManager(AuthenticationConfiguration config) -> AuthenticationManager throws Exception
   body:
   {
       return config.getAuthenticationManager();
   }
4. userDetailsService() -> UserDetailsService
   body:
   {
       return username -> loginRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
   }

NOTE: This is a @Configuration or @ControllerAdvice class. Special rules:
- Test ONLY the @Bean/@ExceptionHandler methods listed in METHODS TO TEST above.
- Do NOT mock Spring Security DSL builders (HttpSecurity, WebSecurityConfigurerAdapter, etc.).
- Do NOT call protected or internal methods on framework objects (DaoAuthenticationProvider.getUserDetailsService(), etc.).
- For @Bean methods: call them on `subject` and assert the returned object type or behaviour.
- For methods that accept parameters (e.g. AuthenticationConfiguration): mock the parameter, stub its methods.

COVERAGE RULES per method:
- assert the actual return value (not just non-null)
- CRITICAL — assert ONLY what the code actually does: read the method body carefully.
  If the method always returns the same value (e.g. `return new ResponseEntity<>(x, HttpStatus.OK)`),
  assert exactly that value. Do NOT assert status codes or return values that the code cannot produce.
- cover each branch (if/else, orElse, ternary, guard clause)
- use assertThrows for exception paths
- use verify() on mocks when the observable result is a collaborator call
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
package com.livro.crud.livros_crud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.livro.crud.livros_crud.auth.LoginRepository;

@Configuration
public class SecurityManager {

	@Autowired
	private LoginRepository loginRepository;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return username -> loginRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado") );
	}

}