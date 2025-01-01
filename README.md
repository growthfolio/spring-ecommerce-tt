# E-commerce - Projeto de Teste Técnico com Spring

Este repositório contém o código-fonte de um projeto de e-commerce desenvolvido como parte de um **teste técnico**. O objetivo é demonstrar habilidades no uso do framework Spring para criação de APIs RESTful, gerenciamento de dados e implementação de funcionalidades comuns em plataformas de comércio eletrônico.

## 📋 Funcionalidades

- **Gerenciamento de Produtos**: Cadastro, listagem, atualização e remoção de produtos.
- **Categorias**: Organização de produtos em categorias para melhor gerenciamento.
- **Cadastro de Usuários**: Registro e gerenciamento de usuários do sistema.
- **Carrinho de Compras**:
  - Adição, remoção e atualização de itens no carrinho.
  - Cálculo automático do total do carrinho.
  - Criação automática de carrinhos para usuários sem um carrinho associado.
- **Autenticação e Autorização**: Implementação de segurança com autenticação JWT.
- **Configuração para Produção**: Preparação para uso de variáveis de ambiente em ambientes de produção.

## 🛠️ Tecnologias Utilizadas

- **Backend**: Spring Boot, Spring Data JPA, Spring Security.
- **Banco de Dados**: MySQL.
- **Testes**: JUnit e Mockito para garantir a qualidade do código.
- **Ferramentas de Build**: Maven.
- **Serialização de Dados**: Configuração com Jackson para formatação de datas e tratamento de loops cíclicos.

## 🚀 Como Executar o Projeto

### Pré-requisitos

Antes de começar, você vai precisar ter instalado em sua máquina:

- **Java 17** ou superior.
- **Maven**.
- Um **banco de dados relacional** (MySQL recomendado).
- Uma ferramenta de gerenciamento de banco de dados (como DBeaver ou MySQL Workbench).

### Passos para Configuração

1. Clone este repositório:
   ```bash
   git clone https://github.com/growthfolio/spring-ecommerce-tt.git
   cd spring-ecommerce-tt
   ```

2. Configure o `application.properties` de 'prod' para 'dev' para utilizar o ambiente de desenvolvimento:
   ```properties
   spring.profiles.active=dev
   ```


3. Configure o banco de dados no arquivo `application.properties.dev`:
   ```properties.dev
   spring.jpa.hibernate.ddl-auto=update
   spring.datasource.url=jdbc:mysql://localhost:3306/nome_do_banco
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
   spring.jpa.show-sql=true
   spring.jackson.date-format=yyyy-MM-dd HH:mm:ss
   spring.jackson.time-zone=Brazil/East
   ```

4. Compile e execute o projeto:
   ```bash
   mvn spring-boot:run
   ```

5. Acesse a aplicação:
   - API disponível em: `http://localhost:8080`

### Variáveis de Ambiente (opcional)

Caso prefira configurar variáveis de ambiente, substitua os valores sensíveis do `application.properties` por placeholders, como `${DB_USERNAME}`, e configure as variáveis no ambiente da máquina.

## 🧪 Testes - Implementação futura

Execute os testes unitários com o comando:
```bash
mvn test
```

## 📂 Estrutura do Projeto

```
src/main/java
├── com
│   └── distribuidorasafari
│       ├── controller   # Controladores da API
│       ├── model        # Entidades do Banco de Dados
│       ├── repository   # Repositórios
│       ├── service      # Serviços
│       └── security     # Configurações de Segurança e Aplicação
```

## 🚧 Ultimas Melhorias Implementadas

- **Refatoração do Carrinho**:
  - Uso de `DTOs` para evitar loops de serialização e melhorar a estrutura das respostas.
  - Configuração do `Hibernate.initialize` para inicializar associações `LAZY`.
  - Implementação de `@JsonIgnoreProperties` em modelos sensíveis para evitar ciclos de dependência.
  - Criação automática de carrinhos para novos usuários ao fazer login ou realizar ações no carrinho.

- **Melhoria no Repositório do Carrinho**:
  - Uso de `@EntityGraph` no repositório para carregar associações necessárias.

## 📜 Licença

Este projeto está sob a licença [MIT](LICENSE). Veja o arquivo LICENSE para mais detalhes.

---

