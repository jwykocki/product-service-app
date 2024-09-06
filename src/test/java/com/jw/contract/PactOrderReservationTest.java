//package com.jw.contract;
//
//import au.com.dius.pact.provider.junit5.PactVerificationContext;
//import au.com.dius.pact.provider.junitsupport.Provider;
//import au.com.dius.pact.provider.junitsupport.State;
//import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
//import au.com.dius.pact.provider.spring.spring6.PactVerificationSpring6Provider;
//import au.com.dius.pact.provider.spring.spring6.Spring6MockMvcTestTarget;
//import com.jw.service.ReservationService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.TestTemplate;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.test.context.ActiveProfiles;
//
//@ActiveProfiles("test")
//@Provider("productService")
//@PactFolder("pacts")
//public class PactOrderReservationTest {
//
//    @Mock
//    private ReservationService reservationService;
//
//    @InjectMocks
//    private ReservationController reservationController;
//
//    @TestTemplate
//    @ExtendWith(PactVerificationSpring6Provider.class)
//    void pactVerificationTestTemplate(PactVerificationContext context) {
//        context.verifyInteraction();
//    }
//
//    @BeforeEach
//    void before(PactVerificationContext context) {
//        try (var mock = MockitoAnnotations.openMocks(this)) {
//            Spring6MockMvcTestTarget testTarget = new Spring6MockMvcTestTarget();
//            testTarget.setControllers(reservationController);
//            context.setTarget(testTarget);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @State("test POST")
//    public void toPostState() {}
//}
