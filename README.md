# Sistema de Registro da Guilda de Aventureiros

## Sobre o Projeto

Durante séculos, a Guilda de Aventureiros manteve seus registros em pergaminhos espalhados por salões, cofres e mesas de taverna. Nomes riscados às pressas, níveis anotados a carvão, companheiros esquecidos nas margens do papel.

Com o aumento das expedições e o surgimento de novas ameaças, o Conselho da Guilda decidiu que não bastava mais confiar na memória dos escribas. Era necessário um **Registro Oficial**.

## Funcionalidades

### Gerenciamento de Aventureiros
- ✅ Registro de novos aventureiros
- ✅ Consulta detalhada por ID
- ✅ Listagem com filtros avançados
- ✅ Atualização de informações
- ✅ Encerramento e reativação de vínculos

### Sistema de Filtros
- **Classe**: Filtrar por classe específica
- **Status**: Distinguir ativos dos inativos
- **Nível**: Buscar por nível mínimo
- **Paginação**: Headers `X-Page` e `X-Size` para controle

### Validações Rigorosas
- ✅ Nome obrigatório e não vazio
- ✅ Classe deve pertencer ao conjunto permitido
- ✅ Nível maior ou igual a 1
- ✅ Validação de parâmetros de paginação

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot**
- **Spring Web** - Controllers REST
- **Spring Validation** - Validações customizadas
- **Maven** - Gerenciamento de dependências

## Estrutura do Projeto

```
src/main/java/br/com/infnet/dr1tp1/
├── controller/
│   └── AventureiroController.java
├── dto/
│   ├── AventureiroDTO.java
│   ├── AventureiroResumoDTO.java
│   └── CompanheiroDTO.java
├── enums/
│   └── Classes.java
├── exception/
│   └── AventureiroNotFoundException.java
├── model/
│   ├── Aventureiro.java
│   └── Companheiro.java
├── repository/
│   └── AventureiroRepository.java
├── service/
│   └── AventureiroService.java
└── validation/
    └── AventureiroValidator.java
```

## Endpoints da API

### Registrar Aventureiro
```http
POST /aventureiros
Content-Type: application/json

{
  "nome": "Aragorn",
  "classe": "RANGER",
  "nivel": 20
}
```

### Listar Aventureiros
```http
GET /aventureiros?classe=WARRIOR&ativo=true&nivelMinimo=10
Headers:
  X-Page: 0
  X-Size: 10
```

### Consultar por ID
```http
GET /aventureiros/1
```

### Atualizar Aventureiro
```http
PUT /aventureiros/1
Content-Type: application/json

{
  "nome": "Aragorn Rei",
  "classe": "RANGER",
  "nivel": 25
}
```

### Encerrar Vínculo
```http
PUT /aventureiros/1/encerrar
```

### Recrutar Novamente
```http
PUT /aventureiros/1/recrutar
```

## Classes Permitidas

- `GUERREIRO`
- `MAGO`
- `ARQUEIRO`
- `CLERIGO`
- `LADINO`
- 
## Regras de Negócio

### Registro
- Aventureiros iniciam como **ativos** automaticamente
- ID é gerado pelo sistema
- Validações aplicadas antes do registro

### Atualização
- Apenas nome, classe e nível podem ser alterados
- Status ativo/inativo possui endpoints específicos
- Companheiros mantêm vinculação existente

### Listagem
- Suporte a filtros opcionais combinados
- Paginação obrigatória via headers
- Metadados de paginação na resposta

## Headers de Resposta

```
X-Page: 0
X-Size: 10
X-Total-Count: 150
X-Total-Pages: 15
```

## Como Executar

1. Certifique-se de ter Java 21+ e Maven instalados
2. Clone o repositório
3. Execute:
```bash
mvn spring-boot:run
```

A API estará disponível em `http://localhost:8080`

## Validações Implementadas

- **Nome**: Obrigatório e não vazio
- **Classe**: Deve ser uma das classes permitidas
- **Nível**: Maior ou igual a 1
- **Paginação**: Página ≥ 0, Tamanho entre 1 e 50

---
