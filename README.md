<img width="1080" height="500" alt="Untitled" src="https://github.com/user-attachments/assets/f36bba3a-7e8a-470c-9e9d-86327d10e670" />

# Sistema de Registro da Guilda de Aventureiros

## Sobre o Projeto

Durante séculos, a Guilda de Aventureiros manteve seus registros em pergaminhos espalhados por salões, cofres e mesas de taverna. Nomes riscados às pressas, níveis anotados a carvão, companheiros esquecidos nas margens do papel.

Com o aumento das expedições e o surgimento de novas ameaças, o Conselho da Guilda decidiu que não bastava mais confiar na memória dos escribas. Era necessário um **Registro Oficial**.

## Funcionalidades

### Gerenciamento de Aventureiros
- ✅ **Registro de novos aventureiros** - Com geração automática de ID
- ✅ **Consulta detalhada por ID** - Inclui informações do companheiro (se existir)
- ✅ **Listagem com filtros avançados** - Por classe, status ativo e nível mínimo
- ✅ **Atualização de informações** - Nome, classe e nível
- ✅ **Encerramento de vínculos** - Desativação de aventureiros
- ✅ **Reativação** - Recrutar aventureiros novamente

### Gerenciamento de Companheiros
- ✅ **Invocação de companheiros** - Adicionar companheiro a um aventureiro
- ✅ **Banimento de companheiros** - Remover companheiro de um aventureiro
- ✅ **Validação de espécies** - Controle das espécies permitidas
- ✅ **Sistema de lealdade** - Níveis de lealdade do companheiro

### Sistema de Filtros e Paginação
- ✅ **Filtro por classe** - GUERREIRO, MAGO, ARQUEIRO, CLERIGO, LADINO
- ✅ **Filtro por status** - Aventureiros ativos ou inativos
- ✅ **Filtro por nível mínimo** - Buscar por experiência
- ✅ **Paginação** - Informações de total de páginas, total de elementos e página atual
- ✅ **Metadados de paginação** - Headers de resposta com informações completas
- ✅ **Lista vazia para páginas inexistentes** - Tratamento adequado

Este projeto é uma API REST para gerenciar uma guilda de aventureiros, suas missões e recompensas.

## Tecnologias Utilizadas

- **Java 25**
- **Spring Boot**: Framework principal para construção da aplicação.
- **Spring Data JPA**: Para persistência de dados e abstração de repositórios.
- **PostgreSQL**: Banco de dados principal para produção e desenvolvimento.
- **Flyway**: Ferramenta para versionamento e migração de schema do banco de dados.
- **H2 Database**: Banco de dados em memória utilizado para a execução de testes de integração automatizados.
- **Lombok**: Para reduzir código boilerplate (getters, setters, construtores).
- **JUnit 5**: Para a escrita de testes unitários e de integração.

## Estrutura do Projeto

O projeto está organizado em módulos lógicos, com uma separação clara entre as responsabilidades de cada camada (Controllers, Services, Repositories, Entities).

### Banco de Dados e Migrações

A estrutura do banco de dados é gerenciada pelo **Flyway**.

- **Produção/Desenvolvimento (`db/migration`)**: Os scripts localizados em `src/main/resources/db/migration` contêm as migrações para o banco de dados **PostgreSQL**. Eles são responsáveis por criar e evoluir o schema da aplicação principal.

- **Testes (`db/migration-h2`)**: Para garantir um ambiente de testes isolado e rápido, utilizamos o banco de dados em memória **H2**. Os scripts de migração específicos para o H2 estão em `src/test/resources/db/migration-h2`. Eles possuem a sintaxe SQL compatível com o H2 e são executados automaticamente antes dos testes de integração.

## Configuração do Ambiente de Teste

Para rodar os testes, nenhuma configuração manual é necessária. O projeto está configurado para:
1.  Ativar o perfil `test` do Spring Boot.
2.  Subir uma instância do banco de dados H2 em memória.
3.  Executar o **Flyway** para limpar o banco (`clean`) e aplicar as migrações (`migrate`) a partir da pasta `db/migration-h2`, criando todo o schema e populando com dados de teste.
4.  Executar os testes de repositório (`@DataJpaTest`) contra essa base de dados limpa e pré-configurada.

Isso garante que os testes sejam consistentes, repetíveis e não interfiram no banco de dados de desenvolvimento ou produção.

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

### 📈 Relatórios (`/relatorios`)

-   **`GET /ranking`**: Gera um ranking de aventureiros baseado em seu desempenho em missões. Permite filtros por `status` da missão e por `período` (data de início e término).

    **Exemplos de Uso:**

    -   **Ranking geral (sem filtros):**
        `/relatorios/ranking`

    -   **Ranking de missões concluídas:**
        `/relatorios/ranking?status=CONCLUIDA`

    -   **Ranking em um período específico:**
        `/relatorios/ranking?inicio=2026-01-01T00:00:00&termino=2026-03-31T23:59:59`

    -   **Ranking de missões em andamento em um período:**
        `/relatorios/ranking?status=EM_ANDAMENTO&inicio=2026-03-01T00:00:00&termino=2026-03-30T23:59:59`

-   **`GET /missoes`**: Gera um relatório de missões, mostrando o total de participantes e a recompensa total. Permite filtrar por `período`.

    **Exemplos de Uso:**

    -   **Relatório geral (sem filtros):**
        `/relatorios/missoes`

    -   **Relatório de missões em um período específico:**
        `/relatorios/missoes?inicio=2026-01-01T00:00:00&termino=2026-03-31T23:59:59`

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
