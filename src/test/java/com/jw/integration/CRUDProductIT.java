package com.jw.integration;

import static com.jw.integration.IntegrationTestFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;

import com.jw.dto.response.ProductResponse;
import com.jw.dto.response.ProductsResponse;
import com.jw.entity.Product;
import com.jw.service.ProductMapper;
import com.jw.service.ProductRepository;
import java.util.List;
import org.apache.hc.core5.http.HttpStatus;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class CRUDProductIT {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    MockMvc mockMvc;

    private static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass")
            .withInitScript("init-products.sql");

    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRESQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRESQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRESQL_CONTAINER::getPassword);
    }

    @BeforeAll
    public static void setup() {
        POSTGRESQL_CONTAINER.start();
    }

    @BeforeEach
    @AfterEach
    public void cleanDatabase() {
        productRepository.deleteAll();
        assertThat(productRepository.findAll()).isEmpty();
    }

    @Test
    public void shouldSaveProductToDatabase() throws Exception {

        // given
        assertThat(productRepository.findAll()).isEmpty();

        // when
        var result = callEndpointAndReturn(HttpMethod.POST, PRODUCT_ENDPOINT, CREATE_PRODUCT_REQUEST);
        assertThat(result.getStatus()).isEqualTo(HttpStatus.SC_OK);

        // then
        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(1);
        assertTestProductCorrectness(products.get(0));
    }

    @Test
    public void shouldGetProductFromDatabase() throws Exception {

        // given
        saveTestProductAndReturn();

        // when
        var result = callEndpointAndReturn(HttpMethod.GET, PRODUCT_ENDPOINT, EMPTY_BODY);

        // then
        List<ProductResponse> productsResponse = new ObjectMapper()
                .readValue(result.getContentAsString(), ProductsResponse.class)
                .getProducts();
        assertThat(productsResponse).hasSize(1);
        assertTestProductCorrectness(productMapper.toProduct(productsResponse.get(0)));
    }

    @Test
    public void shouldGetProductByIdFromDatabase() throws Exception {

        // given
        long id = saveTestProductAndReturn().getProductid();

        // when
        var result = callEndpointAndReturn(HttpMethod.GET, PRODUCT_ENDPOINT + "/" + id, EMPTY_BODY);

        // then
        assertThat(result.getStatus()).isEqualTo(HttpStatus.SC_OK);
        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(1);
        assertTestProductCorrectness(products.get(0));
    }

    @Test
    public void shouldUpdateProduct() throws Exception {

        // given
        long id = saveTestProductAndReturn().getProductid();

        // when
        var result = callEndpointAndReturn(HttpMethod.PUT, PRODUCT_ENDPOINT + "/" + id, UPDATE_PRODUCT_REQUEST);

        // then
        assertThat(result.getStatus()).isEqualTo(HttpStatus.SC_OK);
        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(1).first().extracting("name").isEqualTo(UPDATED_PRODUCT_NAME);
    }

    @Test
    public void shouldDeleteProductFromDatabase() throws Exception {

        // given
        long id = saveTestProductAndReturn().getProductid();

        // when
        var result = callEndpointAndReturn(HttpMethod.DELETE, PRODUCT_ENDPOINT + "/" + id, EMPTY_BODY);

        // then
        assertThat(result.getStatus()).isEqualTo(HttpStatus.SC_OK);
        assertThat(productRepository.findAll()).isEmpty();
    }

    private MockHttpServletResponse callEndpointAndReturn(HttpMethod method, String path, String content)
            throws Exception {
        ResultActions result =
                mockMvc.perform(request(method, path).content(content).contentType(MediaType.APPLICATION_JSON_VALUE));
        return result.andReturn().getResponse();
    }

    private Product saveTestProductAndReturn() {
        productRepository.save(getTestProduct());
        List<Product> productsSaved = productRepository.findAll();
        assertThat(productsSaved).hasSize(1);
        return productsSaved.get(0);
    }
}
