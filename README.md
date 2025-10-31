# 🏥 MedStock - Sistema de Gestão de Estoques Hospitalares

**Tecnologias:** Java | Spring Boot | Oracle | Maven | JUnit

---

## 👥 Equipe

| Nome                             | Função        |
| -------------------------------- | ------------- |
| Jean Matheus Mohamed de Oliveira | Desenvolvedor |
| Pedro Henrique Ribeiro Sampaio   | Desenvolvedor |

---

## 🎯 Objetivo

O **MedStock** é uma aplicação desenvolvida em **Java (Spring Boot)** para o controle eficiente de materiais hospitalares.
O sistema permite **cadastro, atualização, exclusão e movimentação de materiais**, além de gerenciar **usuários com autenticação e controle de perfis (Admin e Operador)**.
Visa otimizar o gerenciamento de inventário hospitalar, garantindo rastreabilidade e controle de estoque em tempo real.

---

## ✨ Funcionalidades Principais

* **Gerenciamento de Materiais:** CRUD completo (Criar, Ler, Atualizar, Deletar) de materiais hospitalares.
* **Movimentações:** Registro de entradas e saídas com vínculo a usuários e datas.
* **Autenticação de Usuários:** Login seguro com perfis ADMIN e OPERADOR.
* **Relatórios:** Histórico de movimentações para auditoria.
* **Validações e Erros:** Regras de negócio aplicadas (quantidades positivas, campos obrigatórios).
* **Banco de Dados:** Persistência com Oracle Database.

---

## ⚙️ Tecnologias Utilizadas

| Tecnologia      | Finalidade                                           |
| --------------- | ---------------------------------------------------- |
| Java 17         | Linguagem principal                                  |
| Spring Boot     | Framework para APIs RESTful                          |
| Maven           | Gerenciador de dependências                          |
| Oracle Database | Banco de dados relacional                            |
| JUnit 5         | Testes unitários e de integração                     |
| Postman         | Testes manuais de API                                |
| JDBC            | Conexão direta com o banco (via `ConnectionFactory`) |

---

## 🧩 Arquitetura

O projeto segue o padrão **Layered Architecture (Arquitetura em Camadas)**:

| Camada         | Responsabilidade                                          |
| -------------- | --------------------------------------------------------- |
| **Controller** | Expõe endpoints REST e trata requisições HTTP             |
| **Service**    | Contém regras de negócio e lógica de processamento        |
| **DAO**        | Executa operações CRUD no banco de dados via JDBC         |
| **Model**      | Representa entidades de domínio                           |
| **Util**       | Classes utilitárias (ex.: `ConnectionFactory`)            |
| **Exception**  | Tratamento global de exceções com `@RestControllerAdvice` |

Essa estrutura promove separação de responsabilidades, testes mais fáceis e manutenção simplificada.

---

## 🗄️ Modelo de Banco de Dados (Oracle)

### **Tabela USUARIO**

| Campo  | Tipo                        | Descrição                                     |
| ------ | --------------------------- | --------------------------------------------- |
| ID     | NUMBER (PK, Auto-increment) | Identificador único                           |
| NOME   | VARCHAR2(255)               | Nome completo                                 |
| LOGIN  | VARCHAR2(100)               | Login (único)                                 |
| SENHA  | VARCHAR2(100)               | Senha (texto simples - usar hash em produção) |
| PERFIL | VARCHAR2(50)                | ADMIN / OPERADOR                              |

---

### **Tabela MATERIAL**

| Campo           | Tipo                        | Descrição                     |
| --------------- | --------------------------- | ----------------------------- |
| ID              | NUMBER (PK, Auto-increment) | Identificador único           |
| NOME            | VARCHAR2(255)               | Nome do material              |
| QUANTIDADE      | NUMBER(10,2)                | Quantidade em estoque         |
| UNIDADE         | VARCHAR2(20)                | Unidade de medida             |
| PONTO_REPOSICAO | NUMBER(10,2)                | Quantidade mínima para alerta |

---

### **Tabela MOVIMENTACAO**

| Campo       | Tipo                        | Descrição                   |
| ----------- | --------------------------- | --------------------------- |
| ID          | NUMBER (PK, Auto-increment) | Identificador único         |
| ID_MATERIAL | NUMBER (FK)                 | Material movimentado        |
| ID_USUARIO  | NUMBER (FK)                 | Usuário responsável         |
| QUANTIDADE  | NUMBER(10,2)                | Quantidade movimentada      |
| TIPO        | VARCHAR2(20)                | ENTRADA / SAIDA             |
| DATA_HORA   | TIMESTAMP                   | Data e hora da movimentação |

---

### **Tabela CONSUMO**

| Campo            | Tipo                        | Descrição                |
| ---------------- | --------------------------- | ------------------------ |
| ID               | NUMBER (PK, Auto-increment) | Identificador único      |
| ID_MATERIAL      | NUMBER (FK)                 | Material consumido       |
| QUANTIDADE       | NUMBER(10,2)                | Quantidade consumida     |
| DATA_APONTAMENTO | TIMESTAMP                   | Data do apontamento      |
| RESPONSAVEL      | VARCHAR2(255)               | Responsável pelo consumo |

---

### **Dados Iniciais**

| Tipo             | Valor                                                                |
| ---------------- | -------------------------------------------------------------------- |
| Usuário admin    | login: `admin` • senha: `123` • perfil: `ADMIN`                      |
| Material exemplo | nome: `Água` • quantidade: `100.00 ml` • ponto de reposição: `20.00` |

---

## 🔗 Endpoints da API

Base URL: `http://localhost:8080/api/`

### **Materiais**

| Método | Endpoint          | Descrição                | Exemplo Corpo                                                                   |
| ------ | ----------------- | ------------------------ | ------------------------------------------------------------------------------- |
| GET    | `/materiais`      | Lista todos os materiais | —                                                                               |
| GET    | `/materiais/{id}` | Busca por ID             | —                                                                               |
| POST   | `/materiais`      | Cria material            | `{"nome": "Soro", "quantidade": 50.0, "unidade": "ml", "pontoReposicao": 10.0}` |
| PUT    | `/materiais/{id}` | Atualiza material        | Mesmo corpo do POST                                                             |
| DELETE | `/materiais/{id}` | Exclui material          | —                                                                               |

---

### **Movimentações**

| Método | Endpoint         | Descrição             | Exemplo Corpo                                                              |
| ------ | ---------------- | --------------------- | -------------------------------------------------------------------------- |
| GET    | `/movimentacoes` | Lista movimentações   | —                                                                          |
| POST   | `/movimentacoes` | Registra movimentação | `{"idMaterial": 1, "idUsuario": 1, "quantidade": 10.0, "tipo": "ENTRADA"}` |

---

### **Usuários**

| Método | Endpoint          | Descrição            | Exemplo Corpo                                                                        |
| ------ | ----------------- | -------------------- | ------------------------------------------------------------------------------------ |
| POST   | `/usuarios/login` | Autentica usuário    | `{"login": "admin", "senha": "123"}`                                                 |
| GET    | `/usuarios`       | Lista usuários       | —                                                                                    |
| GET    | `/usuarios/{id}`  | Busca usuário por ID | —                                                                                    |
| POST   | `/usuarios`       | Cria usuário         | `{"nome": "João Silva", "login": "joao", "senha": "senha123", "perfil": "OPERADOR"}` |
| PUT    | `/usuarios/{id}`  | Atualiza usuário     | Mesmo corpo do POST                                                                  |
| DELETE | `/usuarios/{id}`  | Exclui usuário       | —                                                                                    |

> **Notas:**
>
> * Autenticação simples (use JWT em produção).
> * Retorno em JSON.
> * Códigos HTTP indicam erros (400, 404, 500).

---

## 🚀 Como Executar o Projeto

### **Pré-requisitos**

* Java 17+
* Maven
* Oracle Database
* Postman ou similar

### **Instalação**

```bash
git clone https://github.com/JeannMatheuss/Sprint4-MedStock.git
cd Sprint4-MedStock
```

### **Configuração do Banco de Dados**

Execute os scripts SQL no Oracle Database.

Defina variáveis no `ConnectionFactory.java` ou via ambiente:

```bash
ORACLE_URL=jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl
ORACLE_USER=seu_usuario
ORACLE_PASSWORD=sua_senha
```

### **Execução**

```bash
mvn clean install
mvn spring-boot:run
```

A aplicação será iniciada em **[http://localhost:8080](http://localhost:8080)**.

---

## 🧪 Testes

Executar testes unitários e de integração:

```bash
mvn test
```

**Cobertura:** services, DAOs e controllers.

| Teste                     | Descrição                             |
| ------------------------- | ------------------------------------- |
| `MaterialServiceTest`     | Criação e busca de materiais          |
| `MovimentacaoServiceTest` | Registro e validação de movimentações |
| `UsuarioControllerTest`   | Login via MockMvc                     |

---

## 📁 Estrutura do Projeto

```
medstock/
├── src/main/java/com/fiap/stock/
│   ├── controller/          # Controllers REST
│   ├── dao/                 # Acesso a dados (DAO)
│   ├── exception/           # Tratamento de exceções
│   ├── model/               # Entidades (Material, Movimentacao, Usuario, Consumo)
│   ├── service/             # Lógica de negócio
│   ├── util/                # Utilitários (ConnectionFactory)
│   └── StockApplication.java # Classe principal
├── src/test/java/com/fiap/stock/test/  # Testes unitários
├── pom.xml                 # Dependências Maven
└── README.md               # Este arquivo
```
