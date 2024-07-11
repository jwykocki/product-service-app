package com.jw.contract;

import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.loader.PactUrl;
import com.jw.ProductServiceAppApplication;
import com.jw.controller.ReservationController;
import com.jw.dto.reservation.OrderProductRequest;
import com.jw.dto.reservation.ProductReservationRequest;
import com.jw.service.ReservationService;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.context.ConfigurableWebApplicationContext;

import static org.mockito.ArgumentMatchers.any;

@Provider("productService")
@PactFolder("pacts")
@SpringBootTest

public class PactOrderReservationTest {

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    ReservationController reservationController;

    @BeforeEach
    void before(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", 8082, "/"));

    }

    @AfterEach
    void after(PactVerificationContext context) {
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        MockitoAnnotations.initMocks(this);
        ReservationService reservationService = Mockito.mock(ReservationService.class);
        Mockito.doNothing().when(reservationService).processReservationRequest(any());
        context.verifyInteraction();
        Mockito.verify(reservationService, Mockito.times(1)).processReservationRequest(any());

    }

    @BeforeAll
    public static void start() {
        ConfigurableWebApplicationContext application =
                (ConfigurableWebApplicationContext) SpringApplication.run(ProductServiceAppApplication.class);
    }

    @State("test POST")
    public void toPostState() {

    }
}
