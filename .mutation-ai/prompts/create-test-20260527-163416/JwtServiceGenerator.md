Generate a JUnit 5 + Mockito unit test file for the Java class below.
Output ONLY raw Java code. No markdown, no explanation, no code fences.

TEST CLASS: JwtServiceGeneratorTest
PACKAGE: com.livro.crud.livros_crud.config

IMPORTS TO USE (exact FQNs from source class — do not invent others):
  java.security.Key
  java.util.Date
  java.util.HashMap
  java.util.Map
  java.util.function.Function
  org.springframework.security.core.userdetails.UserDetails
  com.livro.crud.livros_crud.auth.Usuario
  io.jsonwebtoken.Claims
  io.jsonwebtoken.Jwts
  io.jsonwebtoken.SignatureAlgorithm
  io.jsonwebtoken.io.Decoders
  io.jsonwebtoken.security.Keys

METHODS TO TEST (call subject.<method> with EXACTLY the parameter types listed — do not invent overloads):
1. gerarPayload(Usuario usuario) -> Map<String,Object>
   body:
   {
       //AQUI VOCÊ PODE COLOCAR O QUE MAIS VAI COMPOR O PAYLOAD DO TOKEN
       Map<String, Object> payloadData = new HashMap<>();
       payloadData.put("username", usuario.getUsername());
       payloadData.put("id", usuario.getId().toString());
       payloadData.put("role", usuario.getRole());
       payloadData.put("outracoisa", "teste");
       return payloadData;
   }
2. generateToken(Usuario usuario) -> String
   body:
   {
       Map<String, Object> payloadData = this.gerarPayload(usuario);
       return Jwts.builder().setClaims(payloadData).setSubject(usuario.getUsername()).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(new Date().getTime() + 3600000 * this.HORAS_EXPIRACAO_TOKEN)).signWith(getSigningKey(), this.ALGORITMO_ASSINATURA).compact();
   }
3. isTokenValid(String token, UserDetails userDetails) -> boolean
   body:
   {
       final String username = extractUsername(token);
       return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
   }
4. extractUsername(String token) -> String
   body:
   {
       return extractClaim(token, Claims::getSubject);
   }
5. extractClaim(String token, Function<Claims,T> claimsResolver) -> T
   body:
   {
       final Claims claims = extractAllClaims(token);
       return claimsResolver.apply(claims);
   }

FORBIDDEN — DO NOT CALL FROM TEST (private/protected — causes compilation error):
  extractAllClaims(...)
  isTokenExpired(...)
  extractExpiration(...)
  getSigningKey(...)

COVERAGE RULES per method:
- assert the actual return value (not just non-null)
- CRITICAL — assert ONLY what the code actually does: read the method body carefully.
  If the method always returns the same value (e.g. `return new ResponseEntity<>(x, HttpStatus.OK)`),
  assert exactly that value. Do NOT assert status codes or return values that the code cannot produce.
- cover each branch (if/else, orElse, ternary, guard clause)
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

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.livro.crud.livros_crud.auth.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceGenerator {

	public static final String SECRET_KEY = "UMACHAVESECRETADASUAAPIAQUIUMACHAVESECRETADASUAAPIAQUIUMACHAVESECRETADASUAAPIAQUIUMACHAVESECRETADASUAAPIAQUI";
	public static final SignatureAlgorithm ALGORITMO_ASSINATURA = SignatureAlgorithm.HS256;
	public static final int HORAS_EXPIRACAO_TOKEN = 1;

	public Map<String, Object> gerarPayload(Usuario usuario){

		Map<String, Object> payloadData = new HashMap<>();
		payloadData.put("username", usuario.getUsername());
		payloadData.put("id", usuario.getId().toString());
		payloadData.put("role", usuario.getRole());
		payloadData.put("outracoisa", "teste");

		return payloadData;
	}

	public String generateToken(Usuario usuario) {

		Map<String, Object> payloadData = this.gerarPayload(usuario);

		return Jwts
				.builder()
				.setClaims(payloadData)
				.setSubject(usuario.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(new Date().getTime() + 3600000 * this.HORAS_EXPIRACAO_TOKEN))
				.signWith(getSigningKey(), this.ALGORITMO_ASSINATURA)
				.compact();
	}

	private Claims extractAllClaims(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private Key getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(this.SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String extractUsername(String token) {
		return extractClaim(token,Claims::getSubject);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

}