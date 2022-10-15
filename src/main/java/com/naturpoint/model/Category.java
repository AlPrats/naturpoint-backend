package com.naturpoint.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCategory;

    @Column(length = 25,nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String photoUrl;

    @Column(nullable = false)
    private String description;
}
