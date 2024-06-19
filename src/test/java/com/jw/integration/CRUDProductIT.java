package com.jw.integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.jw.service.ProductRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Import({CRUDProductITConfig.class})
@ActiveProfiles("test")
public class CRUDProductIT {

    private final ProductRepository productRepository;

    private final PostgreSQLContainer<?> postgreSQLContainer;

    @Autowired
    public CRUDProductIT(ProductRepository productRepository, PostgreSQLContainer<?> postgreSQLContainer) {
        this.productRepository = productRepository;
        this.postgreSQLContainer = postgreSQLContainer;
    }

    @BeforeAll
    public static void setup() {

    }

    @Test
    public void testCRUDProduct() {
        assertThat(productRepository.count()).isEqualTo(0);
    }
}
