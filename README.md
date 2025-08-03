# 🛒 Spring E-commerce TT - API de E-commerce Completa

## 🎯 Objetivo de Aprendizado
Projeto desenvolvido como **teste técnico** para estudar **Spring Boot avançado**, implementando uma API completa de e-commerce com carrinho de compras, autenticação JWT, relacionamentos complexos e otimizações de performance.

## 🛠️ Tecnologias Utilizadas
- **Framework:** Spring Boot, Spring Security, Spring Data JPA
- **Linguagem:** Java 17+
- **Banco de Dados:** MySQL
- **Autenticação:** JWT (JSON Web Tokens)
- **Testes:** JUnit 5, Mockito
- **Build:** Maven
- **Conceitos estudados:**
  - Spring Boot APIs RESTful
  - Relacionamentos JPA complexos
  - DTOs e serialização JSON
  - Otimização de queries (N+1 problem)
  - Carrinho de compras persistente
  - Configuração de ambientes (dev/prod)

## 🚀 Demonstração
```java
// Repository com EntityGraph para otimização
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    @EntityGraph(attributePaths = {"items", "items.product", "items.product.category"})
    Optional<Cart> findByUserId(Long userId);
    
    @Query("SELECT c FROM Cart c LEFT JOIN FETCH c.items WHERE c.user.id = :userId")
    Optional<Cart> findByUserIdWithItems(@Param("userId") Long userId);
}

// Service com lógica de negócio
@Service
public class CartService {
    
    @Transactional
    public CartDTO addItemToCart(Long userId, Long productId, Integer quantity) {
        Cart cart = findOrCreateCart(userId);
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));
        
        CartItem existingItem = cart.getItems().stream()
            .filter(item -> item.getProduct().getId().equals(productId))
            .findFirst()
            .orElse(null);
        
        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            cart.getItems().add(newItem);
        }
        
        Cart savedCart = cartRepository.save(cart);
        return convertToDTO(savedCart);
    }
}
```

## 💡 Principais Aprendizados

### 🛒 E-commerce Architecture
- **Carrinho Persistente:** Carrinho salvo no banco de dados
- **Relacionamentos:** User -> Cart -> CartItems -> Products
- **DTOs:** Evitar loops de serialização JSON
- **Auto-creation:** Criação automática de carrinho para novos usuários

### 🔄 Otimização de Performance
- **EntityGraph:** Carregamento otimizado de relacionamentos
- **Lazy Loading:** Inicialização sob demanda
- **N+1 Problem:** Resolução com JOIN FETCH
- **Hibernate.initialize:** Inicialização manual de coleções

### 📊 Gerenciamento de Estado
- **Transações:** @Transactional para consistência
- **Cascade Operations:** Propagação de operações
- **Orphan Removal:** Limpeza automática de itens órfãos

## 🧠 Conceitos Técnicos Estudados

### 1. **Relacionamentos JPA Complexos**
```java
@Entity
@Table(name = "tb_carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"cart", "password"})
    private User user;
    
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("cart")
    private List<CartItem> items = new ArrayList<>();
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

@Entity
@Table(name = "tb_cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonIgnoreProperties("items")
    private Cart cart;
    
    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnoreProperties("cartItems")
    private Product product;
    
    @Column(nullable = false)
    private Integer quantity;
}
```

### 2. **DTOs para Evitar Loops de Serialização**
```java
public class CartDTO {
    private Long id;
    private Long userId;
    private List<CartItemDTO> items;
    private BigDecimal total;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public static CartDTO fromEntity(Cart cart) {
        CartDTO dto = new CartDTO();
        dto.setId(cart.getId());
        dto.setUserId(cart.getUser().getId());
        dto.setItems(cart.getItems().stream()
            .map(CartItemDTO::fromEntity)
            .collect(Collectors.toList()));
        dto.setTotal(calculateTotal(cart.getItems()));
        dto.setCreatedAt(cart.getCreatedAt());
        dto.setUpdatedAt(cart.getUpdatedAt());
        return dto;
    }
}

public class CartItemDTO {
    private Long id;
    private ProductDTO product;
    private Integer quantity;
    private BigDecimal subtotal;
    
    public static CartItemDTO fromEntity(CartItem item) {
        CartItemDTO dto = new CartItemDTO();
        dto.setId(item.getId());
        dto.setProduct(ProductDTO.fromEntity(item.getProduct()));
        dto.setQuantity(item.getQuantity());
        dto.setSubtotal(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        return dto;
    }
}
```

### 3. **Configuração de Ambientes**
```properties
# application-dev.properties
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_dev
spring.datasource.username=dev_user
spring.datasource.password=dev_pass
spring.jpa.show-sql=true
spring.jackson.date-format=yyyy-MM-dd HH:mm:ss
spring.jackson.time-zone=Brazil/East

# application-prod.properties
spring.jpa.hibernate.ddl-auto=validate
spring.datasource.url=${DATABASE_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.show-sql=false
```

## 📁 Estrutura do Projeto
```
spring-ecommerce-tt/
├── src/main/java/
│   └── com/distribuidorasafari/ecommerce/
│       ├── controller/          # Controllers REST
│       │   ├── ProductController.java
│       │   ├── CategoryController.java
│       │   ├── UserController.java
│       │   └── CartController.java
│       ├── model/              # Entidades JPA
│       │   ├── Product.java
│       │   ├── Category.java
│       │   ├── User.java
│       │   ├── Cart.java
│       │   └── CartItem.java
│       ├── repository/         # Repositórios
│       ├── service/            # Serviços de negócio
│       ├── dto/                # Data Transfer Objects
│       └── security/           # Configurações de segurança
├── src/main/resources/
│   ├── application.properties
│   ├── application-dev.properties
│   └── application-prod.properties
└── src/test/java/             # Testes unitários
```

## 🔧 Como Executar

### Pré-requisitos
- Java 17+
- Maven
- MySQL

### Configuração
```bash
# Clone o repositório
git clone <repo-url>
cd spring-ecommerce-tt

# Configure o perfil de desenvolvimento
# Em application.properties:
spring.profiles.active=dev

# Configure o banco em application-dev.properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_dev
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

# Execute a aplicação
mvn spring-boot:run
```

## 🛒 Funcionalidades do Carrinho

### Endpoints Principais
```bash
# Adicionar item ao carrinho
POST /cart/add
{
  "productId": 1,
  "quantity": 2
}

# Visualizar carrinho
GET /cart

# Atualizar quantidade
PUT /cart/item/{itemId}
{
  "quantity": 3
}

# Remover item
DELETE /cart/item/{itemId}

# Limpar carrinho
DELETE /cart/clear
```

## 🚧 Desafios Enfrentados
1. **N+1 Problem:** Resolvido com EntityGraph e JOIN FETCH
2. **Loops de Serialização:** Solucionado com DTOs e @JsonIgnoreProperties
3. **Lazy Loading:** Gerenciamento correto de sessões Hibernate
4. **Carrinho Persistente:** Lógica de criação automática
5. **Performance:** Otimização de queries complexas
6. **Transações:** Garantir consistência em operações múltiplas

## 📚 Recursos Utilizados
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA Reference](https://spring.io/projects/spring-data-jpa)
- [Hibernate User Guide](https://hibernate.org/orm/documentation/)
- [Jackson Documentation](https://github.com/FasterXML/jackson-docs)
- [MySQL Documentation](https://dev.mysql.com/doc/)

## 📈 Próximos Passos
- [ ] Implementar sistema de pedidos (Order/OrderItem)
- [ ] Adicionar sistema de pagamento
- [ ] Implementar estoque de produtos
- [ ] Criar sistema de cupons de desconto
- [ ] Adicionar histórico de compras
- [ ] Implementar notificações por email

## 🔗 Projetos Relacionados
- [React E-commerce](../react-ecommerce-tt/) - Frontend da aplicação
- [Spring Blog Platform](../spring-blog-platform/) - Outro projeto Spring
- [Spring Bookstore](../spring-bookstore-management/) - Sistema de livraria

---

**Desenvolvido por:** Felipe Macedo  
**Contato:** contato.dev.macedo@gmail.com  
**GitHub:** [FelipeMacedo](https://github.com/felipemacedo1)  
**LinkedIn:** [felipemacedo1](https://linkedin.com/in/felipemacedo1)

> 💡 **Reflexão:** Este projeto consolidou meus conhecimentos avançados em Spring Boot. A implementação de um carrinho persistente com otimizações de performance me ensinou conceitos fundamentais para sistemas e-commerce reais.