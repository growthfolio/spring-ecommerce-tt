package com.distribuidorasafari.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O campo nome é obrigatorio")
    @Size(min = 3, max = 80, message = "O nome deve conter entre 3 e 80 Caracteres")
    @Column(length = 80)
    private String name;

    @Schema(example = "email@email.com.br")
    @NotBlank(message = "O campo e-mail é obrigatorio")
    @Email(message = "O atributo email deve ser um email válido")
    @Size(max = 255, message = "O atributo email deve conter no maximo 255 caracteres")
    @Column(length = 255)
    private String email;

    @NotBlank(message = "O campo senha é obrigatorio")
    @Size(min = 6, max = 120, message = "O campo senha deve conter no minimo 6 caracteres ")
    @Column(length = 120)
    private String password;

    @NotBlank(message = "O campo cpf_cnpj é obrigatório")
    @Size(min = 11, max = 40, message = "O campo cpf-cnpj deve conter no máximo 40 caracteres")
    @Column(length = 40)
    private String cpf_cnpj;

    @NotBlank(message = "O atributo tipo é obrigatório")
    @Size(max = 15, message = "O atributo tipo deve conter no máximo 15 caracteres")
    @Column(length = 15)
    private String type;

    @Size(max = 5000, message = "O link da foto não pode ser maior do que 5000 caracteres")
    @Column(length = 5000)
    private String photo;

    @UpdateTimestamp
    private LocalDateTime data;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("user")
    private List<Product> products;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    @JsonIgnoreProperties("user")
    private Cart cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("user")
    private List<Order> orders;
   
    // Getters and setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCpf_cnpj() {
		return cpf_cnpj;
	}

	public void setCpf_cnpj(String cpf_cnpj) {
		this.cpf_cnpj = cpf_cnpj;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}


}
