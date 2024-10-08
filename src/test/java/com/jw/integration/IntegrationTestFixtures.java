package com.jw.integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.jw.entity.Product;

class IntegrationTestFixtures {

    static final String PRODUCT_ENDPOINT = "/product";
    static final String CREATE_PRODUCT_NAME = "testName";
    static final String UPDATED_PRODUCT_NAME = "updatedTestName";

    static final String CREATE_PRODUCT_REQUEST =
            """
            {
                "name": "%s",
                "reserved": 0,
                "available": 5
            }
            """
                    .formatted(CREATE_PRODUCT_NAME);

    static String UPDATE_PRODUCT_REQUEST =
            """
            {
                "name": "%s",
                "reserved": 0,
                "available": 5
            }
            """
                    .formatted(UPDATED_PRODUCT_NAME);

    static final int CREATE_PRODUCT_RESERVED = 0;
    static final int CREATE_PRODUCT_AVAILABLE = 5;
    static final String EMPTY_BODY = "";

    static Product getTestProduct() {
        Product product = new Product();
        product.setName(CREATE_PRODUCT_NAME);
        product.setReserved(CREATE_PRODUCT_RESERVED);
        product.setAvailable(CREATE_PRODUCT_AVAILABLE);
        return product;
    }

    static void assertTestProductCorrectness(Product product) {
        assertThat(product.getProductid()).isNotNull();
        assertThat(product.getName()).isEqualTo(CREATE_PRODUCT_NAME);
        assertThat(product.getReserved()).isEqualTo(CREATE_PRODUCT_RESERVED);
        assertThat(product.getAvailable()).isEqualTo(CREATE_PRODUCT_AVAILABLE);
    }
}
