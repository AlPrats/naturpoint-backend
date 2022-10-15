package com.naturpoint.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProduct;

    @Generated(GenerationTime.INSERT)
    @Column(unique = true, updatable = false)
    private Integer sn;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String photoUrl;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_category", nullable = false, foreignKey = @ForeignKey(name = "FK_PRODUCT_CATEGORY"))
    private Category category;
}
