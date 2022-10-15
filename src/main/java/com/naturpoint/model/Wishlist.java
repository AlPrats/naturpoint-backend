package com.naturpoint.model;

import com.naturpoint.security.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wishlist")
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idWishlist;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user", referencedColumnName = "idUser")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_product", referencedColumnName = "idProduct")
    private Product product;


    private LocalDateTime createdDate;

    public Wishlist(User user, Product product) {
        this.user = user;
        this.product = product;
        this.createdDate = LocalDateTime.now();
    }
}
