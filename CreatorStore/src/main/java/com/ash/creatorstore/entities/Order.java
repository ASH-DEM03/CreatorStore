package com.ash.creatorstore.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Column(name ="customer_name",nullable = false)
    private String customerName;

    @Column(name ="customer_email",nullable = false)
    private String customerEmail;

    @Column(name ="customer_phone",nullable = false)
    private String customerPhone;

    @Column(name ="customer_adress",nullable = false)
    private String customerAddress;

    @Column(nullable = false)
    private String status;

    @Column(name ="total_price",nullable = false)
    private BigDecimal totalPrice;

    @JsonManagedReference
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItems> orderItems;

    @Column(name ="created_at",nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();

    }

}
