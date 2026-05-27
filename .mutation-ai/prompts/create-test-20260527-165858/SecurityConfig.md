Generate a JUnit 5 + Mockito unit test file for the Java class below.
Output ONLY raw Java code. No markdown, no explanation, no code fences.

TEST CLASS: SecurityConfigTest
PACKAGE: com.livro.crud.livros_crud.config

REQUIRED CLASS SKELETON — copy this EXACTLY, then fill in the @Test methods:
@ExtendWith(MockitoExtension.class)
public class SecurityConfigTest {

    @Mock
    private JwtAuthenticationFilter jwtAuthFilter;
    @Mock
    private AuthenticationProvider authenticationProvider;

    @InjectMocks
    private SecurityConfig subject;

    // @Test methods go here
}
NOTE: field injection — Mockito injects @Mock fields into @InjectMocks via reflection.
Do NOT use @SpringBootTest or @Autowired in the test.

IMPORTS TO USE (exact FQNs from source class — do not invent others):
  java.util.Arrays
  org.springframework.boot.web.servlet.FilterRegistrationBean
  org.springframework.context.annotation.Bean
  org.springframework.context.annotation.Configuration
  org.springframework.http.HttpHeaders
  org.springframework.http.HttpMethod
  org.springframework.security.authentication.AuthenticationProvider
  org.springframework.security.config.annotation.web.builders.HttpSecurity
  org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
  org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
  org.springframework.security.config.http.SessionCreationPolicy
  org.springframework.security.web.SecurityFilterChain
  org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
  org.springframework.web.cors.CorsConfiguration
  org.springframework.web.cors.UrlBasedCorsConfigurationSource
  org.springframework.web.filter.CorsFilter
  org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity

METHODS TO TEST (call subject.<method> with EXACTLY the parameter types listed — do not invent overloads):
1. securityFilterChain(HttpSecurity http) -> SecurityFilterChain throws Exception
   body:
   {
       http.csrf(AbstractHttpConfigurer::disable).cors(AbstractHttpConfigurer::disable).authorizeHttpRequests((requests) -> requests.requestMatchers("/api/login").permitAll().requestMatchers("/api/register").permitAll().anyRequest().authenticated()).authenticationProvider(authenticationProvider).addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class).sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
       return http.build();
   }
2. corsFilter() -> FilterRegistrationBean<CorsFilter>
   body:
   {
       UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
       CorsConfiguration config = new CorsConfiguration();
       config.setAllowCredentials(true);
       config.setAllowedOriginPatterns(Arrays.asList("*"));
       config.setAllowedHeaders(Arrays.asList(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE, HttpHeaders.ACCEPT));
       config.setAllowedMethods(Arrays.asList(HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(), HttpMethod.DELETE.name()));
       config.setMaxAge(3600L);
       source.registerCorsConfiguration("/**", config);
       FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(source));
       bean.setOrder(-102);
       return bean;
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
package com.livro.crud.livros_crud.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig  {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
		.csrf(AbstractHttpConfigurer::disable)
		.cors(AbstractHttpConfigurer::disable)
		.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/api/login").permitAll()
				.requestMatchers("/api/register").permitAll()
				.anyRequest().authenticated())
		.authenticationProvider(authenticationProvider)
		.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
		.sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return http.build();
	}

	@Autowired
	private JwtAuthenticationFilter jwtAuthFilter;

	@Autowired
	private AuthenticationProvider authenticationProvider;

	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.setAllowedOriginPatterns(Arrays.asList("*"));
		config.setAllowedHeaders(Arrays.asList(HttpHeaders.AUTHORIZATION,HttpHeaders.CONTENT_TYPE,HttpHeaders.ACCEPT));
		config.setAllowedMethods(Arrays.asList(HttpMethod.GET.name(),HttpMethod.POST.name(),HttpMethod.PUT.name(),HttpMethod.DELETE.name()));
		config.setMaxAge(3600L);
		source.registerCorsConfiguration("