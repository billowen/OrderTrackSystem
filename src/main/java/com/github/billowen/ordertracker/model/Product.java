package com.github.billowen.ordertracker.model;

import javax.persistence.*;

@Entity
@Table(name = "projects")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
}
