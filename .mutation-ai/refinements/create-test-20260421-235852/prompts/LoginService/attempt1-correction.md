Você está corrigindo um teste unitário Java que falhou na validação.

Objetivo:
Corrigir o teste abaixo para que ele fique coerente com o código-fonte real, estruturalmente completo e compilável no contexto do projeto.

Tentativa atual: 1 de 3

Regras obrigatórias de saída:
- responda com apenas código Java puro
- não inclua markdown
- não inclua explicações
- retorne um único arquivo Java completo e compilável
- preserve package, imports e nome da classe de teste

Motivos da rejeição:
- LoginServiceTest usa RuntimeException genérica sem evidência na implementação

Instruções de correção:
- corrija comportamentos incompatíveis com a implementação real
- corrija imports ausentes e static imports ausentes
- não use símbolos sem import correspondente, exceto classes do mesmo package ou de java.lang
- se o teste não compilou, priorize corrigir estrutura do arquivo, imports e símbolos não resolvidos
- se o fluxo real indicar exceção, use assertThrows(...)
- para métodos void, não capture retorno; valide com verify(...)
- quando houver delegação para dependências, valide interações relevantes com verify(...)
- não use assertNull quando o fluxo real não retornar null explicitamente

Classe alvo:
- fullyQualifiedName: com.livro.crud.livros_crud.auth.LoginService
- sourceFile: src/main/java/com/livro/crud/livros_crud/auth/LoginService.java

Código fonte da classe alvo:
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

Teste anterior rejeitado:
package com.livro.crud.livros_crud.auth;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {

    @Mock
    private LoginRepository repository;

    @Mock
    private JwtServiceGenerator jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private LoginService loginService;

    @BeforeEach
    public void setUp() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(repository.findByUsername(anyString())).thenReturn(Optional.of(new Usuario()));
    }

    @Test
    public void testLogar_Success() {
        String username = "testUser";
        String password = "testPass";
        Login login = new Login(username, password);

        String token = loginService.logar(login);

        assertNotNull(token);
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(repository).findByUsername(eq(username));
        verify(jwtService).generateToken(any(Usuario.class));
    }

    @Test
    public void testLogar_RepositoryReturnsNull() {
        when(repository.findByUsername(anyString())).thenReturn(Optional.empty());

        Login login = new Login("testUser", "testPass");

        assertThrows(RuntimeException.class, () -> loginService.logar(login));

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(repository).findByUsername(eq("testUser"));
    }

    @Test
    public void testLogar_AuthenticationFails() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new BadCredentialsException("Invalid credentials"));

        Login login = new Login("testUser", "testPass");

        assertThrows(BadCredentialsException.class, () -> loginService.logar(login));

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}