Você é um gerador de testes unitários Java.

Tarefa:
Gere um único arquivo de teste unitário completo para a classe informada abaixo.

Plano explícito antes da geração:
- classe alvo: com.livro.crud.livros_crud.service.LivroService
- package alvo: com.livro.crud.livros_crud.service
- construtor principal: nenhum construtor explícito identificado
- dependências de construtor: []
- dependências de field: [LivroRepository livroRepository]
- dependências consolidadas: [LivroRepository livroRepository]
- métodos públicos a cobrir: [saveLivro, findAllLivros, findByIdLivro, update, delete, findByTitulo, findByAno, findByAutor, findByAnoMaiorQue]
- usa Optional: true
- usa exceções: false
- imports reais disponíveis: [java.util.List, java.util.Optional, org.springframework.beans.factory.annotation.Autowired, org.springframework.stereotype.Service, com.livro.crud.livros_crud.entity.Autor, com.livro.crud.livros_crud.entity.Livro, com.livro.crud.livros_crud.repository.LivroRepository]

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
- o nome da classe de teste deve ser LivroServiceTest
- mantenha no teste o mesmo package da classe alvo
- gere código compatível com src/test/java
- se houver dependências colaboradoras, use @Mock nelas quando fizer sentido
- use @InjectMocks na classe sob teste quando fizer sentido
- use apenas tipos, imports e dependências que realmente existam no código fornecido
- todo tipo usado no teste deve ter import explícito quando não estiver no mesmo package ou em java.lang
- se usar AutorRepository, LivroRepository, Autor, Livro, Login, Usuario, AuthenticationManager, Optional, JwtServiceGenerator ou qualquer outro tipo, inclua o import correto
- não invente nomes de repositório, serviço, DTO, entidade ou collaborator
- se uma dependência não estiver evidente, prefira usar exatamente os fields e imports listados na análise estrutural
- package obrigatório no arquivo de teste: com.livro.crud.livros_crud.service
- quando a classe alvo depender de um colaborador com retorno Optional, use Optional.of(...) ou Optional.empty() no mock, nunca o valor cru

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
- fullyQualifiedName: com.livro.crud.livros_crud.service.LivroService
- sourceFile: src/main/java/com/livro/crud/livros_crud/service/LivroService.java

Dependências identificadas (prioridade para dependências de construtor):
- LivroRepository livroRepository

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
- com.livro.crud.livros_crud.entity.Livro
- com.livro.crud.livros_crud.repository.LivroRepository
- fields de dependência reais:
- LivroRepository livroRepository

Métodos públicos a testar (com corpo real para guiar os cenários de teste):
1. saveLivro(Livro livro) -> Livro
   corpo:
   {
       return livroRepository.save(livro);
   }

2. findAllLivros() -> List<Livro>
   corpo:
   {
       return livroRepository.findAll();
   }

3. findByIdLivro(Long id) -> Livro
   corpo:
   {
       Optional<Livro> livro = livroRepository.findById(id);
       return livro.orElse(null);
   }

4. update(Long id, Livro livro) -> Livro
   corpo:
   {
       Livro livroExistente = this.findByIdLivro(id);
       livroExistente.setTitulo(livro.getTitulo());
       livroExistente.setAno(livro.getAno());
       return livroRepository.save(livroExistente);
   }

5. delete(Long id) -> void
   corpo:
   {
       this.livroRepository.deleteById(id);
   }

6. findByTitulo(String titulo) -> List<Livro>
   corpo:
   {
       return this.livroRepository.findByTitulo(titulo);
   }

7. findByAno(int ano) -> List<Livro>
   corpo:
   {
       return this.livroRepository.findByAno(ano);
   }

8. findByAutor(long idAutor) -> List<Livro>
   corpo:
   {
       Autor autor = new Autor();
       autor.setId(idAutor);
       return this.livroRepository.findByAutor(autor);
   }

9. findByAnoMaiorQue(int ano) -> List<Livro>
   corpo:
   {
       return this.livroRepository.findByAnoMaiorQue(ano);
   }

Para cada método acima, leia o corpo e gere testes que cubram:
- caminho feliz (retorno esperado com assertivas sobre o valor retornado)
- cada branch condicional (if/else, ternário, condição de guarda)
- exceções lançadas (verifique a mensagem ou tipo quando relevante)
- Optional vazio e Optional com valor quando o método usar Optional
- verificações de comportamento em mocks (verify) quando a chamada ao colaborador for o resultado observável

Use rigorosamente os nomes acima para mocks, imports e collaborators.
Antes de finalizar, confira se todos os tipos usados no teste foram importados explicitamente.

Código fonte da classe alvo:
package com.livro.crud.livros_crud.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.livro.crud.livros_crud.entity.Autor;
import com.livro.crud.livros_crud.entity.Livro;
import com.livro.crud.livros_crud.repository.LivroRepository;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    public Livro saveLivro(Livro livro){
        return livroRepository.save(livro);
    }

    public List<Livro> findAllLivros(){
        return livroRepository.findAll();
    }

    public Livro findByIdLivro(Long id){
        Optional<Livro> livro = livroRepository.findById(id);
        return livro.orElse(null);
    }

    public Livro update(Long id, Livro livro){
        Livro livroExistente = this.findByIdLivro(id);
        livroExistente.setTitulo(livro.getTitulo());
        livroExistente.setAno(livro.getAno());
        return livroRepository.save(livroExistente);
    }

    public void delete(Long id){
        this.livroRepository.deleteById(id);
    }

    public List<Livro> findByTitulo(String titulo){
        return this.livroRepository.findByTitulo(titulo);
    }

    public List<Livro> findByAno(int ano){
        return this.livroRepository.findByAno(ano);
    }

    public List<Livro> findByAutor(long idAutor){
        Autor autor = new Autor();
        autor.setId(idAutor);
        return this.livroRepository.findByAutor(autor);
    }

    public List<Livro> findByAnoMaiorQue(int ano){
        return this.livroRepository.findByAnoMaiorQue(ano);
    }

}