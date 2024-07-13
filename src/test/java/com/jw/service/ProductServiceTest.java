package com.jw.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import com.jw.dto.ProductRequest;
import com.jw.entity.Product;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ProductServiceTest {

    @Inject
    ProductService productService;

    @MockBean
    private DbService dbService;

    @BeforeEach
    void setUp() {}

    @AfterEach
    void tearDown() {}

    @Test
    public void saveProduct() {
        // given
        Mockito.when(dbService.saveProduct(any(Product.class))).thenReturn(new Product());
        ProductRequest productRequest = new ProductRequest("testProductName", 10, 20);

        // when
        productService.saveProduct(productRequest);

        // then
        ArgumentCaptor<Product> argument = ArgumentCaptor.forClass(Product.class);
        verify(dbService).saveProduct(argument.capture());
        assertThat(argument.getValue())
                .usingRecursiveComparison()
                .ignoringFields("productid")
                .isEqualTo(productRequest);
    }

    @Test
    void getAllProducts() {}

    @Test
    void getProductById() {}

    @Test
    void updateProduct() {}

    @Test
    void deleteProduct() {}
}
