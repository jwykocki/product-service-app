package com.jw.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_table")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productid;

    private String name;

    private int reserved;

    private int available;
}
