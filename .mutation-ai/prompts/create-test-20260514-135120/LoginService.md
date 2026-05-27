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

Restrições de implementação:
- use JUnit 5
- use Mockito
- use explicitamente @Mock e @InjectMocks quando fizer sentido
- use preferencialmente @ExtendWith(MockitoExtension.class) quando fizer sentido
- prefira testes unitários puros
- não suba contexto Spring sem necessidade estrita
- o nome da classe de teste deve ser LoginServiceTest
- mantenha no teste o mesmo package da classe alvo
- gere código compatível com src/test/java
- se houver dependências colaboradoras, use @Mock nelas quando fizer sentido
- use @InjectMocks na classe sob teste quando fizer sentido
- use apenas tipos, imports e dependências que realmente existam no código fornecido
- não invente nomes de repositório, serviço, DTO, entidade ou collaborator
- se uma dependência não estiver evidente, prefira usar exatamente os fields e imports listados na análise estrutural

Objetivo dos testes:
- cobrir comportamento observável
- cobrir caminho feliz
- cobrir falhas relevantes
- cobrir bordas importantes
- cobrir retornos nulos quando houver
- cobrir cenários onde dependências retornam null
- cobrir Optional vazio quando aplicável
- cobrir exceções relevantes
- verificar interações com mocks quando isso fizer parte do comportamento observável
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

Análise estrutural da classe:
- package: com.livro.crud.livros_crud.auth
- construtor principal: nenhum construtor explícito identificado
- usa Optional: false
- usa exceções: false
- imports reais:
- org.springframework.beans.factory.annotation.Autowired
- org.springframework.security.authentication.AuthenticationManager
- org.springframework.security.authentication.UsernamePasswordAuthenticationToken
- org.springframework.stereotype.Service
- com.livro.crud.livros_crud.config.JwtServiceGenerator
- fields de dependência reais:
- LoginRepository repository
- JwtServiceGenerator jwtService
- AuthenticationManager authenticationManager
- métodos públicos:
- logar : String(Login login)
- gerarToken : String(Login login)

Use rigorosamente os nomes acima para mocks, imports e collaborators.

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