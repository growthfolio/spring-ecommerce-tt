# E-commerce - Projeto de Teste Técnico com Spring

Este repositório contém o código-fonte de um projeto de e-commerce desenvolvido como parte de um **teste técnico**. O objetivo é demonstrar habilidades no uso do framework Spring para criação de APIs RESTful, gerenciamento de dados e implementação de funcionalidades comuns em plataformas de comércio eletrônico.

## 📋 Funcionalidades

- **Gerenciamento de Produtos**: Cadastro, listagem, atualização e remoção de produtos.
- **Categorias**: Organização de produtos em categorias para melhor gerenciamento.
- **Cadastro de Usuários**: Registro e gerenciamento de usuários do sistema.
- **Carrinho de Compras**: Adição, remoção e visualização de itens no carrinho.
- **Processamento de Pedidos**: Realização e acompanhamento de pedidos realizados.
- **Autenticação e Autorização**: Implementação de segurança com autenticação JWT.

## 🛠️ Tecnologias Utilizadas

- **Backend**: Spring Boot, Spring Data JPA, Spring Security.
- **Banco de Dados**: MySQL ou PostgreSQL.
- **Testes**: JUnit e Mockito para garantir a qualidade do código.
- **Ferramentas de Build**: Maven.

## 🚀 Como Executar o Projeto

### Pré-requisitos

Antes de começar, você vai precisar ter instalado em sua máquina:

- **Java 17** ou superior.
- **Maven**.
- Um **banco de dados relacional** (MySQL ou PostgreSQL recomendado).
- Uma ferramenta de gerenciamento de banco de dados (como DBeaver ou MySQL Workbench).

### Passos para Configuração

1. Clone este repositório:
   ```bash
   git clone https://github.com/seu-usuario/nome-do-repositorio.git
   cd nome-do-repositorio
   ```

2. Configure o banco de dados no arquivo `application.properties` ou `application.yml`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/nome_do_banco
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
   spring.jpa.hibernate.ddl-auto=update
   ```

3. Compile e execute o projeto:
   ```bash
   mvn spring-boot:run
   ```

4. Acesse a aplicação:
   - API disponível em: `http://localhost:8080`

## 🧪 Testes

Execute os testes unitários com o comando:
```bash
mvn test
```

## 📂 Estrutura do Projeto

```
src/main/java
├── com
│   └── exemplo
│       ├── controller   # Controladores da API
│       ├── dto          # Objetos de Transferência de Dados
│       ├── entity       # Entidades do Banco de Dados
│       ├── repository   # Repositórios
│       ├── service      # Serviços
│       └── config       # Configurações de Segurança e Aplicação
```

## 🤝 Contribuição

Contribuições são sempre bem-vindas! Sinta-se à vontade para abrir issues ou enviar pull requests. Antes de contribuir, leia o arquivo [CONTRIBUTING.md](CONTRIBUTING.md) (se aplicável).

## 📜 Licença

Este projeto está sob a licença [MIT](LICENSE). Veja o arquivo LICENSE para mais detalhes.

---
