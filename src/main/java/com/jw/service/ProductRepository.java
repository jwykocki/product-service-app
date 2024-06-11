package com.jw.service;

import com.jw.entity.Product;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.ApplicationScope;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
