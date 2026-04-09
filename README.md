<img width="1080" height="500" alt="Untitled" src="https://github.com/user-attachments/assets/f36bba3a-7e8a-470c-9e9d-86327d10e670" />

# Sistema de Registro da Guilda de Aventureiros

## Sobre o Projeto

Durante séculos, a Guilda de Aventureiros manteve seus registros em pergaminhos espalhados por salões, cofres e mesas de taverna. 
Nomes riscados às pressas, níveis anotados a carvão, companheiros esquecidos nas margens do papel.
Com o aumento das expedições e o surgimento de novas ameaças, o Conselho da Guilda decidiu que não bastava mais confiar na memória dos escribas.
Era necessário um **Registro Oficial**.

## Funcionalidades

### Gerenciamento de Aventureiros e Missões
- ✅ **CRUD completo para Aventureiros**: Registro, consulta, listagem, atualização e desativação/reativação.
- ✅ **CRUD completo para Companheiros**: Associação de um companheiro a um aventureiro, com validação de espécie e sistema de lealdade.
- ✅ **CRUD completo para Missões**: Criação, consulta, listagem, atualização e remoção.
- ✅ **Sistema de Participação**: Associa aventureiros a missões com papéis (`LIDER`, `SUPORTE`) e recompensas específicas.

### Busca e Análise de Dados com Elasticsearch
- ✅ **Busca Avançada na Loja**: Métodos de busca por texto (`match`, `phrase`, `multi-match`) e busca "fuzzy" (por aproximação) para encontrar itens.
- ✅ **Consultas Combinadas**: Endpoint de busca avançada que permite filtrar itens por `categoria`, `raridade` e `faixa de preço` simultaneamente.
- ✅ **Analytics da Loja**: Endpoints dedicados para métricas e agregações em tempo real:
    - Contagem de itens por categoria.
    - Contagem de itens por raridade.
    - Cálculo de preço médio de todos os itens.
    - Agrupamento de itens por faixas de preço customizadas.

### Performance e Cache com Redis
- ✅ **Cache de Consultas Frequentes**: Armazena em cache os resultados de endpoints de alto custo, como o painel tático de missões (`/paineis/missoes/top15dias`), diminuindo a carga no banco de dados.
- ✅ **Estratégia de Evicção Programada**: Um serviço agendado (`@Scheduled`) limpa todos os caches da aplicação periodicamente (configurado para meia-noite), garantindo que os dados sejam atualizados regularmente.
- ✅ **Abstração de Cache**: O `CacheManager` do Spring abstrai a implementação, permitindo que o Redis opere de forma transparente e possa ser substituído por outro provedor de cache se necessário.

### Sistema de Filtros e Paginação
- ✅ **Filtros Avançados**: Todos os principais endpoints de listagem (`/aventureiros/filtro`, `/missoes`, etc.) suportam múltiplos parâmetros de filtro.
- ✅ **Paginação Completa**: As respostas de listagem são paginadas, incluindo no corpo do JSON informações como `totalElements`, `totalPages`, `currentPage`, etc.
- ✅ **Metadados de Paginação**: Headers HTTP (`X-Total-Count`, `X-Total-Pages`) também são enviados na resposta para facilitar a integração com front-ends.

## Tecnologias Utilizadas

- **Java 25**
- **Spring Boot**: Framework principal para construção da aplicação.
- **Spring Data JPA**: Para persistência de dados e abstração de repositórios.
- **PostgreSQL**: Banco de dados principal para produção e desenvolvimento.
- **Flyway**: Ferramenta para versionamento e migração de schema do banco de dados.
- **H2 Database**: Banco de dados em memória utilizado para a execução de testes de integração automatizados.
- **Redis**: Para cache de dados, melhorando a performance de consultas frequentes.
- **Elasticsearch**: Para indexação e busca eficiente de dados, especialmente para filtros avançados.
- **Lombok**: Para reduzir código boilerplate (getters, setters, construtores).
- **JUnit 5 e Mockito**: Para a escrita de testes unitários e de integração.

## Estrutura do Projeto

```ascii
src/main/java/br/com/infnet/guilda_dos_aventureiros
├── MainApplication.java
├── controllers/
│   ├── aventura/
│   ├── loja/
│   ├── operacoes/
│   └── relatorios/
├── dto/
│   ├── PagedResponse.java
│   ├── audit/
│   ├── aventura/
│   ├── loja/
│   ├── operacoes/
│   └── relatorios/
├── entities/
│   ├── audit/
│   ├── aventura/
│   ├── loja/
│   └── operacoes/
├── enums/
│   ├── audit/
│   └── aventura/
├── exceptions/
│   ├── BusinessRuleException.java
│   └── EntityNotFoundException.java
├── mapper/
│   ├── PainelTaticoMissaoMapper.java
│   ├── aventura/
│   └── loja/
├── repositories/
│   ├── audit/
│   ├── aventura/
│   └── operacoes/
├── service/
│   ├── aventura/
│   ├── loja/
│   ├── operacoes/
│   └── relatorios/
└── utils/
    └── CacheService.java
```

- **`controllers`**: Responsáveis por expor a API REST, receber requisições, validar entradas e retornar respostas.
- **`dto`**: (Data Transfer Objects) Objetos que carregam dados entre as camadas, garantindo que a lógica de negócio não se acople aos detalhes da API ou da persistência.
- **`entities`**: Classes que representam as tabelas do banco de dados (JPA Entities) e documentos do Elasticsearch.
- **`enums`**: Tipos enumerados para valores constantes, como `AventureiroClasses`, `StatusMissao`, etc.
- **`exceptions`**: Classes de exceção customizadas para um tratamento de erros claro e centralizado.
- **`mapper`**: Conversores que automatizam o mapeamento entre `Entities` e `DTOs`.
- **`repositories`**: Interfaces do Spring Data para acesso ao banco de dados (JPA).
- **`service`**: Contém a lógica de negócio da aplicação. Cada serviço é responsável por uma área funcional específica, como gerenciamento de aventureiros, operações de loja, geração de relatórios, etc.
- **`utils`**: Classes utilitárias, como o `CacheService` para gerenciamento programático do cache.

## Preparação do ambiente
Para essa aplicação funcionar, primeiro precisamos configurar as imagens Docker para PostgreSQL, Elasticsearch e Redis.

### PostgreSQL
1. Vá até o terminal de sua preferência e execute o seguinte comando para baixar a imagem do PostgreSQL utilizada neste projeto:
```bash
docker run --platform linux/arm64 -d --name <NOME_DO_CONTEINER> -p 5432:5432 leogloriainfnet/postgres-tp2-spring:2.0-mac
````
2. Não se esqueça de substituir `<NOME_DO_CONTEINER>` por um nome de sua escolha para o container.
3. Após executar o comando, precisamos mudar a senha do banco para podermos modificar os dados lá presentes.
4. Para isso, acesse o container do PostgreSQL utilizando o comando:
```bash
docker exec -it <NOME_DO_CONTEINER> psql -U postgres bash
```
5. Agora, dentro do terminal do PostgreSQL, execute o seguinte comando para alterar a senha do usuário `postgres`:
```sql
ALTER USER postgres WITH PASSWORD 'SUA_NOVA_SENHA';
```
6. Substitua `SUA_NOVA_SENHA` por uma senha de sua escolha.
7. Pronto. A imagem estará rodando na porta 5432 e você poderá acessar o banco de dados utilizando a nova senha que acabou de configurar.

### Elasticsearch
1. Para baixar a imagem do Elasticsearch, execute o seguinte comando no terminal:
```bash
docker run -d --name <NOME_DO_CONTEINER> -p 9200:9200 -e ES_JAVA_OPTS="-Xms512m -Xmx512m" leogloriainfnet/elastic-tp2-spring:1.0-windows
```
2. Este comando irá baixar a imagem do Elasticsearch e configurá-la para rodar na porta 9200.

### Redis
1. O Redis é utilizado para armazenar dados de cache, garantindo respostas rápidas e eficientes para consultas frequentes.
   Isso diminui a carga no banco de dados e melhora o desempenho geral da aplicação.
   Ele está configurado para ser criado automaticamente quando uma chamada ao ranking de missões é feita,
   e os dados de cache serão apagados a cada 24h.
2. Para baixar a imagem do Redis, execute o seguinte comando no terminal:
```bash
docker run -d --name <NOME_DO_CONTEINER> -p 6379:6379 redis
```
3. Este comando irá baixar a imagem do Redis e configurá-la para rodar na porta 6379.


### Banco de Dados e Migrações

A estrutura do banco de dados é gerenciada pelo **Flyway**.

- **Produção/Desenvolvimento (`db/migration`)**: Os scripts localizados em `src/main/resources/db/migration` contêm as migrações para o banco de dados **PostgreSQL**. 
Eles são responsáveis por criar e evoluir o schema da aplicação principal. 
Caso seja a sua primeira vez rodando a aplicação, é necessária a execução manual dos scripts, em ordem 1-5, para criação das tabelas e população do banco.

- **Testes (`db/migration-h2`)**: Para garantir um ambiente de testes isolado e rápido, utilizamos o banco de dados em memória **H2**. 
Os scripts de migração específicos para o H2 estão em `src/test/resources/db/migration-h2`.
Eles possuem a sintaxe SQL compatível com o H2 e são executados automaticamente antes dos testes de integração.

## Configuração do Ambiente de Teste

Para rodar os testes, nenhuma configuração manual é necessária. O projeto está configurado para:
1.  Ativar o perfil `test` do Spring Boot.
2.  Subir uma instância do banco de dados H2 em memória.
3.  Executar o **Flyway** para limpar o banco (`clean`) e aplicar as migrações (`migrate`) a partir da pasta `db/migration-h2`, 
criando todo o schema e populando com dados de teste.
4.  Executar os testes de repositório (`@SpringbootTest`) contra essa base de dados limpa e pré-configurada.

Isso garante que os testes sejam consistentes, repetíveis e não interfiram no banco de dados de desenvolvimento ou produção.

## Como Executar

### Pré-requisitos
- **Java 25** ou superior
- **Maven 3.6+**

### Executando a Aplicação

1. **Clone o repositório**:
```bash
git clone https://github.com/Shyuu7/Guilda_dos_Aventureiros.git
cd Guilda_dos_Aventureiros
```

2. **Execute com Maven**:
```bash
mvn spring-boot:run
```
A aplicação estará disponível em `http://localhost:8080`.

### Executando os Testes
```bash
mvn test
```
Este comando irá executar todos os testes unitários e de integração, utilizando a configuração de banco de dados H2.

## Endpoints da API

### 🔷 Aventureiros (`/aventureiros`)

-   **`POST /`**: Registra um novo aventureiro.
    ```json
    {
      "nome": "Aragorn",
      "classe": "GUERREIRO",
      "nivel": 15,
      "organizacaoId": 1,
      "usuarioId": 1
    }
    ```

-   **`GET /`**: Lista todos os aventureiros (sem paginação).
-   **`GET /filtro`**: Lista aventureiros com filtros por `classe`, `ativo`, `nivelMinimo` e com paginação.
-   **`GET /buscar`**: Busca aventureiros por nome com paginação.
-   **`GET /{id}`**: Consulta os dados básicos de um aventureiro por ID.
-   **`GET /{id}/perfil`**: Consulta um perfil detalhado do aventureiro, incluindo suas participações em missões.
-   **`PUT /{id}`**: Atualiza os dados de um aventureiro.
    ```json
    {
      "nome": "Aragorn, o Rei",
      "classe": "GUERREIRO",
      "nivel": 20
    }
    ```

-   **`PATCH /{id}/desativar`**: Desativa o cadastro de um aventureiro.
-   **`PATCH /{id}/reativar`**: Reativa o cadastro de um aventureiro.
-   **`POST /{id}/companheiro`**: Adiciona um companheiro a um aventureiro.
    ```json
    {
      "nome": "Brego",
      "especie": "CAVALO",
      "lealdade": 100
    }
    ```

### 🔶 Missões (`/missoes`)

-   **`POST /`**: Cria uma nova missão.
    ```json
    {
      "titulo": "Resgatar o Artefato Perdido",
      "nivelPerigo": "ALTO",
      "status": "DISPONIVEL",
      "dataInicio": "2026-04-01T10:00:00",
      "dataTermino": "2026-04-10T18:00:00",
      "organizacaoId": 1
    }
    ```

-   **`GET /`**: Lista missões com filtros por `status`, `titulo`, `nivelRecomendado` e com paginação.
-   **`GET /{id}`**: Consulta os detalhes de uma missão por ID.
-   **`PUT /{id}`**: Atualiza os dados de uma missão.
    ```json
    {
      "titulo": "Resgatar o Artefato Perdido de Valinor",
      "nivelPerigo": "CRITICO",
      "status": "EM_ANDAMENTO",
      "dataInicio": "2026-04-01T10:00:00",
      "dataTermino": "2026-04-15T20:00:00",
      "organizacaoId": 1
    }
    ```

-   **`DELETE /{id}`**: Remove uma missão do sistema.
-   **`POST /{idMissao}/participantes/{idAventureiro}`**: Adiciona um aventureiro como participante de uma missão.
    ```json
    {
      "papelMissao": "LIDER",
      "recompensaEmOuro": 500.00,
      "destaque": true
    }
    ```

-   **`PUT /{idMissao}/participantes/{idAventureiro}`**: Atualiza os dados da participação de um aventureiro em uma missão (ex: recompensa).
    ```json
    {
      "papelMissao": "LIDER",
      "recompensaEmOuro": 750.50,
      "destaque": true
    }
    ```

-   **`DELETE /{idMissao}/participantes/{idAventureiro}`**: Remove um aventureiro de uma missão.

### 🏪 Loja (`/loja`)

-   **`GET /produtos/busca/nome`**: Realiza uma busca textual simples nos campos `nome` e `descricao`.
-   **`GET /produtos/busca/combinada`**: Permite uma busca combinada com múltiplos critérios: `categoria`, `raridade`, `precoMin` e `precoMax`.
-   **`GET /produtos/agregacoes/por-categoria`**: Agrega e conta o número de itens por categoria.
-   **`GET /produtos/agregacoes/por-raridade`**: Agrega e conta o número de itens por raridade.
-   **`GET /produtos/agregacoes/preco-medio`**: Calcula o preço médio de todos os itens da loja.
-   **`GET /produtos/agregacoes/faixas-preco`**: Agrupa os itens em faixas de preço predefinidas.

### 📈 Operações e Relatórios

-   **`GET /painel-tatico/top15dias`**: Retorna um painel tático com as top 10 missões dos últimos 15 dias ordenadas por índice de prontidão (endpoint cacheado com Redis).
-   **`GET /relatorios/ranking`**: Gera um ranking de aventureiros baseado em seu desempenho em missões. Permite filtros por `status` da missão e por `período`.
-   **`GET /relatorios/missoes`**: Gera um relatório de missões, mostrando o total de participantes e a recompensa total. Permite filtrar por `período`.
