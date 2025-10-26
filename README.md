# 🏥 MedStock - Sistema de Gestão de Estoques Hospitalares

## 👥 Equipe
- Jean Matheus Mohamed de Oliveira
- Pedro Henrique Ribeiro Sampaio 

## 🎯 Objetivo
Aplicação desenvolvida em **Java (Spring Boot)** para o controle de **materiais hospitalares**.  
Permite o **cadastro, atualização, exclusão e movimentação de materiais**, com controle de usuários e autenticação.

## ⚙️ Tecnologias
- **Java 17**
- **Spring Boot**
- **Maven**
- **Oracle Database**
- **JUnit 5 (Testes)**
- **Postman (Testes de API)**

## 🧩 Arquitetura
O projeto segue o padrão **Camadas (Layered Architecture)**:
Controller → Service → DAO → Banco de Dados

- `Controller`: expõe endpoints REST.  
- `Service`: contém as regras de negócio.  
- `DAO`: manipula o banco de dados via JDBC.  
- `ConnectionFactory`: centraliza a criação das conexões.  

## 🔗 Endpoints Principais

| Método | URI | Descrição |
|--------|------|-----------|
| GET | `/api/materiais` | Listar todos os materiais |
| GET | `/api/materiais/{id}` | Buscar material por ID |
| POST | `/api/materiais` | Criar material |
| PUT | `/api/materiais/{id}` | Atualizar material |
| DELETE | `/api/materiais/{id}` | Excluir material |
| GET | `/api/movimentacoes` | Listar movimentações |
| POST | `/api/movimentacoes` | Criar movimentação |
| POST | `/api/usuarios/login` | Login de usuário |

## 🧠 Modelo de Banco de Dados
Tabelas principais:
- **USUARIO (ID, NOME, LOGIN, SENHA, PERFIL)**  
- **MATERIAL (ID, NOME, QUANTIDADE, UNIDADE, PONTO_REPOSICAO)**  
- **MOVIMENTACAO (ID, MATERIAL_ID, USUARIO_ID, TIPO, QUANTIDADE, DATA_MOVIMENTACAO)**  



