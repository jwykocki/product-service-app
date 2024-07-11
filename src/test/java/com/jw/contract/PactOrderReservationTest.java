package com.jw.contract;

import static org.mockito.ArgumentMatchers.any;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import com.jw.ProductServiceAppApplication;
import com.jw.controller.ReservationController;
import com.jw.service.ReservationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.context.ConfigurableWebApplicationContext;

@ActiveProfiles("test")
@Provider("productService")
@PactFolder("pacts")
@SpringBootTest
@ExtendWith(PactVerificationInvocationContextProvider.class)
public class PactOrderReservationTest {

    @MockBean
    private ReservationService reservationService;

    @InjectMocks
    private ReservationController reservationController;

    @BeforeEach
    void before(PactVerificationContext context) {
        MockitoAnnotations.initMocks(this);
        Mockito.doNothing().when(reservationService).processReservationRequest(any());
        context.setTarget(new HttpTestTarget("localhost", 8082, "/"));
    }

    @AfterEach
    void after(PactVerificationContext context) {}

    @TestTemplate
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @BeforeAll
    public static void start() {
        ConfigurableWebApplicationContext application =
                (ConfigurableWebApplicationContext) SpringApplication.run(ProductServiceAppApplication.class);
    }

    @State("test POST")
    public void toPostState() {}
}
