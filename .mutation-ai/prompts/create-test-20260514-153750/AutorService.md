Você é um gerador de testes unitários Java.

Tarefa:
Gere um único arquivo de teste unitário completo para a classe informada abaixo.

Plano explícito antes da geração:
- classe alvo: com.livro.crud.livros_crud.service.AutorService
- package alvo: com.livro.crud.livros_crud.service
- construtor principal: nenhum construtor explícito identificado
- dependências de construtor: []
- dependências de field: [AutorRepository autorRepository]
- dependências consolidadas: [AutorRepository autorRepository]
- métodos públicos a cobrir: [findAllAutors, findByIdAutor, deleteAutor, saveAutor, updateAutor]
- usa Optional: true
- usa exceções: false
- imports reais disponíveis: [java.util.List, java.util.Optional, org.springframework.beans.factory.annotation.Autowired, org.springframework.stereotype.Service, com.livro.crud.livros_crud.entity.Autor, com.livro.crud.livros_crud.repository.AutorRepository]

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
- o nome da classe de teste deve ser AutorServiceTest
- mantenha no teste o mesmo package da classe alvo
- gere código compatível com src/test/java
- se houver dependências colaboradoras, use @Mock nelas quando fizer sentido
- use @InjectMocks na classe sob teste quando fizer sentido
- use apenas tipos, imports e dependências que realmente existam no código fornecido
- todo tipo usado no teste deve ter import explícito quando não estiver no mesmo package ou em java.lang
- se usar AutorRepository, LivroRepository, Autor, Livro, Login, Usuario, AuthenticationManager, Optional, JwtServiceGenerator ou qualquer outro tipo, inclua o import correto
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
- fullyQualifiedName: com.livro.crud.livros_crud.service.AutorService
- sourceFile: src/main/java/com/livro/crud/livros_crud/service/AutorService.java

Dependências identificadas (prioridade para dependências de construtor):
- AutorRepository autorRepository

Análise estrutural da classe:
- package: com.livro.crud.livros_crud.service
- construtor principal: nenhum construtor explícito identificado
- usa Optional: true
- usa exceções: false
- imports reais:
- java.util.List
- java.util.Optional
- org.springframework.beans.factory.annotation.Autowired
- org.springframework.stereotype.Service
- com.livro.crud.livros_crud.entity.Autor
- com.livro.crud.livros_crud.repository.AutorRepository
- fields de dependência reais:
- AutorRepository autorRepository
- métodos públicos:
- findAllAutors : List<Autor>()
- findByIdAutor : Autor(Long id)
- deleteAutor : void(long id)
- saveAutor : Autor(Autor autor)
- updateAutor : Autor(long id, Autor autor)

Use rigorosamente os nomes acima para mocks, imports e collaborators.
Antes de finalizar, confira se todos os tipos usados no teste foram importados explicitamente.

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