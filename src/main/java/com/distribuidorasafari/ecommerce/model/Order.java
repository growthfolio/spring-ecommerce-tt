package com.distribuidorasafari.ecommerce.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

	@Entity
	@Table(name = "orders")
	public class Order {
		
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "user_id", nullable = false)
	    @JsonIgnoreProperties({"orders", "products"})
	    private User user;
	    
	    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	    @JsonIgnoreProperties("order")
	    private List<OrderItem> orderItems = new ArrayList<>();
	    
	    @NotNull
	    @Column(precision = 10, scale = 2)
	    private BigDecimal totalAmount;
	    
	    @NotNull
	    @Enumerated(EnumType.STRING)
	    private OrderStatus status;
	    
	    @Column(updatable = false)
	    @CreationTimestamp
	    private LocalDateTime orderDate;
	    
	    public enum OrderStatus {
	        PENDING, PROCESSING, COMPLETED, CANCELLED
	    }
	    
	    // Métodos auxiliares
	    
	    public void addOrderItem(OrderItem item) {
	        orderItems.add(item);
	        item.setOrder(this);
	    }
	    
	    public void removeOrderItem(OrderItem item) {
	        orderItems.remove(item);
	        item.setOrder(null);
	    }
	    
	    public void confirmOrder() {
	        for (OrderItem item : orderItems) {
	            Product product = item.getProduct();
	            if (product.getAmount() < item.getQuantity()) {
	                throw new IllegalArgumentException("Estoque insuficiente para o produto: " + product.getName());
	            }
	            product.setAmount(product.getAmount() - item.getQuantity());
	        }
	        this.status = OrderStatus.COMPLETED;
	    }
	
	    // Getters and Setters
	    
		public Long getId() {
			return id;
		}
	
		public void setId(Long id) {
			this.id = id;
		}
	
		public User getUser() {
			return user;
		}
	
		public void setUser(User user) {
			this.user = user;
		}
	
		public List<OrderItem> getOrderItems() {
			return orderItems;
		}
	
		public void setOrderItems(List<OrderItem> orderItems) {
			this.orderItems = orderItems;
		}
	
		public BigDecimal getTotalAmount() {
			return totalAmount;
		}
	
		public void setTotalAmount(BigDecimal totalAmount) {
			this.totalAmount = totalAmount;
		}
	
		public OrderStatus getStatus() {
			return status;
		}
	
		public void setStatus(OrderStatus status) {
			this.status = status;
		}
	
		public LocalDateTime getOrderDate() {
			return orderDate;
		}
	
		public void setOrderDate(LocalDateTime orderDate) {
			this.orderDate = orderDate;
		}
	    
	}