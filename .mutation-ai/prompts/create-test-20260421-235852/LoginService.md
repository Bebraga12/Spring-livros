Você é um gerador de testes unitários Java.

Tarefa:
Gere um único arquivo de teste unitário completo para a classe informada abaixo.

Regras obrigatórias de saída:
- responda com apenas código Java puro
- não inclua texto fora do código
- não inclua markdown
- não inclua blocos de código
- não inclua ```java, ``` ou qualquer outro delimitador de código
- não inclua explicações
- não inclua comentários explicando o código gerado
- retorne um único arquivo Java completo e compilável
- o arquivo deve incluir package, imports, declaração da classe de teste e métodos de teste completos
- inclua todos os imports necessários para o arquivo compilar no contexto do projeto
- inclua todos os static imports necessários para assertions do JUnit e utilitários do Mockito quando usados
- não use classes sem import correspondente, exceto classes do mesmo package ou de java.lang

Restrições de implementação:
- use JUnit 5
- use Mockito
- se usar assertEquals, assertThrows, assertNotNull ou assertNull, inclua os static imports apropriados
- se usar verify, when, doThrow, any, eq ou outros utilitários do Mockito, inclua os static imports apropriados
- use explicitamente @Mock e @InjectMocks quando fizer sentido
- use preferencialmente @ExtendWith(MockitoExtension.class) quando fizer sentido
- prefira testes unitários puros
- não suba contexto Spring sem necessidade estrita
- o nome da classe de teste deve ser LoginServiceTest
- mantenha no teste o mesmo package da classe alvo
- gere código compatível com src/test/java
- se houver dependências colaboradoras, use @Mock nelas quando fizer sentido
- use @InjectMocks na classe sob teste quando fizer sentido

Objetivo dos testes:
- cobrir comportamento observável
- cobrir caminho feliz
- cobrir falhas relevantes
- cobrir bordas importantes
- use assertNull apenas quando o cenário de null estiver explicitamente refletido no fluxo da implementação real
- nunca use assertNull em fluxos com Optional.get(), orElseThrow() ou acesso posterior a objeto ausente
- cubra cenários onde dependências retornam null somente quando isso estiver explicitamente refletido no fluxo real
- cubra Optional vazio quando aplicável
- se o fluxo real indicar exceção em cenário not found, use assertThrows
- cubra exceções relevantes
- para métodos void, não capture retorno; valide comportamento com verify(...) e, quando fizer sentido, assertThrows(...)
- não use assertEquals, assertNull ou assertNotNull para validar retorno de métodos void
- verifique interações com mocks quando isso fizer parte do comportamento observável
- quando o método delegar para repository, gateway, client ou outros serviços, valide as interações relevantes com verify(...)
- evite assertNotNull isolado quando houver interações relevantes ou resultado determinístico que possa ser validado de forma mais forte
- quando houver valor determinístico no resultado, prefira assertEquals com o valor esperado
- usar nomes de testes descritivos e legíveis
- evitar testes frágeis
- evitar mocks desnecessários
- não testar detalhes internos irrelevantes

Contexto do alvo:
- fullyQualifiedName: com.livro.crud.livros_crud.auth.LoginService
- sourceFile: src/main/java/com/livro/crud/livros_crud/auth/LoginService.java

Dependências identificadas (prioridade para dependências de construtor):
- LoginRepository repository
- JwtServiceGenerator jwtService
- AuthenticationManager authenticationManager

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