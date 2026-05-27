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
- o nome da classe de teste deve ser AutorServiceTest
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
- fullyQualifiedName: com.livro.crud.livros_crud.service.AutorService
- sourceFile: src/main/java/com/livro/crud/livros_crud/service/AutorService.java

Dependências identificadas (prioridade para dependências de construtor):
- AutorRepository autorRepository

Código fonte da classe alvo:
package com.livro.crud.livros_crud.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.livro.crud.livros_crud.entity.Autor;
import com.livro.crud.livros_crud.repository.AutorRepository;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    public List<Autor> findAllAutors() {
        return autorRepository.findAll();
    }

    public Autor findByIdAutor(Long id) {
        Optional<Autor> autor = autorRepository.findById(id);
        return autor.orElse(null);
    }

    public void deleteAutor(long id) {
        this.autorRepository.deleteById(id);
    }

    public Autor saveAutor(Autor autor) {
        return this.autorRepository.save(autor);
    }

    public Autor updateAutor(long id, Autor autor) {
        Autor autorExistente = this.findByIdAutor(id);
        autorExistente.setNome(autor.getNome());
        autorExistente.setIdade(autor.getIdade());
        return this.autorRepository.save(autorExistente);

    }
}