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
- ✅ **Paginação via headers** - X-Page e X-Size para controle
- ✅ **Metadados de paginação** - Headers de resposta com informações completas
- ✅ **Lista vazia para páginas inexistentes** - Tratamento adequado

### População Automática
- ✅ **100 aventureiros pré-cadastrados** - Gerados automaticamente na inicialização
- ✅ **Nomes aleatórios** - Usando biblioteca JavaFaker com nomes de Elder Scrolls
- ✅ **Classes e níveis diversificados** - Distribuição aleatória

### Validações Rigorosas
- ✅ Nome obrigatório e não vazio
- ✅ Classe deve pertencer ao conjunto permitido
- ✅ Nível maior ou igual a 1
- ✅ Validação de parâmetros de paginação (página ≥ 0, tamanho 1-50)
- ✅ Validação de companheiros (nome, espécie, lealdade)

## Tecnologias Utilizadas

- **Java 25**
- **Spring Boot 4.0.2** - Framework principal
- **Spring Web** - Controllers REST
- **Spring Validation** - Validações customizadas
- **Lombok** - Redução de boilerplate
- **JavaFaker 1.0.2** - Geração de dados fictícios
- **Maven** - Gerenciamento de dependências

## Arquitetura

### Estrutura em Camadas
```
├── Controller    # Endpoints REST (AventureiroController)
├── Service       # Regras de negócio (AventureiroService)
├── Repository    # Acesso a dados (AventureiroRepository)
├── Domain        # Entidades (Aventureiro, Companheiro)
├── DTO          # Objetos de transferência de dados
├── Mapper       # Conversão entre DTOs e entidades
├── Enums        # Classes e Espécies
└── Exceptions   # Tratamento de erros customizados
```

### Fluxo de Dados
1. **Entrada**: Controller recebe requisições HTTP
2. **Validação**: DTOs validam dados de entrada
3. **Processamento**: Service aplica regras de negócio
4. **Persistência**: Repository gerencia dados em memória
5. **Resposta**: Mapper converte entidades para DTOs de resposta

## Endpoints da API

### 🔷 Aventureiros

#### Registrar Aventureiro
```http
POST /aventureiros
Content-Type: application/json

{
  "nome": "Aragorn",
  "classe": "GUERREIRO",
  "nivel": 20
}
```
**Resposta**: Status 201 Created + dados completos do aventureiro

#### Listar Aventureiros (com filtros e paginação)
```http
GET /aventureiros?classe=GUERREIRO&ativo=true&nivelMinimo=10
Headers:
  X-Page: 0
  X-Size: 10
```
**Filtros opcionais**:
- `classe`: GUERREIRO, MAGO, ARQUEIRO, CLERIGO, LADINO
- `ativo`: true/false
- `nivelMinimo`: número inteiro

**Headers de resposta**:
- `X-Page`: Página atual
- `X-Size`: Itens por página
- `X-Total-Count`: Total de itens
- `X-Total-Pages`: Total de páginas

#### Consultar por ID
```http
GET /aventureiros/1
```
**Resposta**: Dados completos incluindo companheiro (se existir)

#### Atualizar Aventureiro
```http
PUT /aventureiros/1
Content-Type: application/json

{
  "nome": "Aragorn Rei",
  "classe": "GUERREIRO",
  "nivel": 25
}
```
**Permite alterar**: nome, classe, nível  
**Não permite alterar**: ID, status ativo, companheiro

#### Desativar Aventureiro
```http
PATCH /aventureiros/1/desativar
```
**Resultado**: Aventureiro fica inativo mas permanece no sistema

#### Reativar Aventureiro
```http
PATCH /aventureiros/1/reativar
```
**Resultado**: Aventureiro volta a ficar ativo

### 🔶 Companheiros

#### Invocar Companheiro
```http
POST /aventureiros/1/companheiro
Content-Type: application/json

{
  "nome": "Fenrir",
  "especie": "LOBO",
  "lealdade": 85
}
```
**Espécies disponíveis**: LOBO, CORUJA, GOLEM, DRAGAO_MINIATURA

#### Banir Companheiro
```http
DELETE /aventureiros/1/companheiro
```
**Resultado**: Remove o companheiro do aventureiro

## Classes e Espécies Permitidas

### Classes de Aventureiros
- `GUERREIRO` - Especialista em combate corpo a corpo
- `MAGO` - Manipulador das artes arcanas  
- `ARQUEIRO` - Mestre em combate à distância
- `CLERIGO` - Curandeiro e protetor divino
- `LADINO` - Especialista em furtividade e precisão

### Espécies de Companheiros
- `LOBO` - Companheiro leal e selvagem
- `CORUJA` - Sábia observadora dos céus
- `GOLEM` - Guardião mágico resistente
- `DRAGAO_MINIATURA` - Pequeno mas feroz dragão

## Regras de Negócio Implementadas

### Aventureiros
- **Registro automático como ativo**: Todo aventureiro inicia ativo automaticamente
- **ID gerado automaticamente**: Sistema gera ID sequencial único
- **Validações rigorosas**: Nome obrigatório, classe válida, nível ≥ 1
- **População inicial**: 100 aventureiros pré-cadastrados com dados aleatórios
- **Nomes fictícios**: Gerados pela biblioteca JavaFaker (Elder Scrolls)

### Atualização de Dados
- **Campos editáveis**: Apenas nome, classe e nível podem ser alterados
- **Campos protegidos**: ID, status ativo e companheiro são imutáveis via atualização
- **Endpoints específicos**: Status ativo possui endpoints dedicados (desativar/reativar)

### Sistema de Listagem
- **Filtros opcionais**: Por classe, status ativo e nível mínimo
- **Filtros combinados**: Múltiplos filtros podem ser aplicados simultaneamente
- **Paginação obrigatória**: Sempre retorna resultados paginados
- **Metadados completos**: Headers informam estado atual da paginação

### Companheiros
- **Vinculação única**: Cada aventureiro pode ter apenas um companheiro
- **Espécies controladas**: Apenas espécies pré-definidas são permitidas
- **Sistema de lealdade**: Valor numérico representa a lealdade do companheiro
- **Gestão independente**: Companheiros possuem endpoints específicos

### Persistência em Memória
- **ArrayList interno**: Dados armazenados em memória durante execução
- **Perda de dados**: Dados são perdidos ao reiniciar a aplicação
- **Performance otimizada**: Acesso rápido aos dados sem overhead de BD

## Headers de Resposta

```
X-Page: 0
X-Size: 10
X-Total-Count: 150
X-Total-Pages: 15
```

## Como Executar

### Pré-requisitos
- **Java 25** ou superior
- **Maven 3.6+**

### Executando a Aplicação

1. **Clone o repositório**:
```bash
git clone https://github.com/Shyuu7/Guilda_dos_Aventureiros.git
```

2. **Execute com Maven**:
```bash
mvn spring-boot:run
```

3. **Alternativa - Compilar e executar**:
```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="br.com.infnet.dr1tp1.MainApplication"
```

### Acesso à API
- **URL Base**: `http://localhost:8080`
- **Endpoints**: `/aventureiros`
- **Dados iniciais**: 100 aventureiros são criados automaticamente na inicialização

### Testando a API

**Listar aventureiros**:
```bash
curl -X GET "http://localhost:8080/aventureiros" \
  -H "X-Page: 0" \
  -H "X-Size: 10"
```

**Criar aventureiro**:
```bash
curl -X POST "http://localhost:8080/aventureiros" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Teste Aventureiro",
    "classe": "GUERREIRO",
    "nivel": 15
  }'
```

## Validações Implementadas

### Aventureiros
- **Nome**: Obrigatório e não pode estar em branco
- **Classe**: Deve ser uma das 5 classes permitidas (GUERREIRO, MAGO, ARQUEIRO, CLERIGO, LADINO)
- **Nível**: Maior ou igual a 1, valor positivo obrigatório

### Paginação
- **Página (X-Page)**: Deve ser maior ou igual a 0
- **Tamanho (X-Size)**: Entre 1 e 50 itens por página

### Companheiros
- **Nome**: Obrigatório e não pode estar em branco
- **Espécie**: Deve ser uma das 4 espécies permitidas (LOBO, CORUJA, GOLEM, DRAGAO_MINIATURA)
- **Lealdade**: Valor numérico positivo ou zero

### Filtros de Busca
- **Classe**: Opcional, deve ser classe válida se informada
- **Status Ativo**: Opcional, boolean (true/false)
- **Nível Mínimo**: Opcional, valor inteiro positivo

### Tratamento de Erros
- **404 Not Found**: Quando aventureiro não existe
- **400 Bad Request**: Para dados inválidos ou violação de validações
- **500 Internal Server Error**: Para erros internos do sistema

---
