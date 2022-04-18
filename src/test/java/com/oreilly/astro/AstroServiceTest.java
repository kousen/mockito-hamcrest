package com.oreilly.astro;

import com.oreilly.astro.json.Assignment;
import com.oreilly.astro.json.AstroResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class AstroServiceTest {
    private final AstroResponse mockAstroResponse =
            new AstroResponse(7, "Success", Arrays.asList(
                    new Assignment("John Sheridan", "Babylon 5"),
                    new Assignment("Susan Ivanova", "Babylon 5"),
                    new Assignment("Beckett Mariner", "USS Cerritos"),
                    new Assignment("Brad Boimler", "USS Cerritos"),
                    new Assignment("Sam Rutherford", "USS Cerritos"),
                    new Assignment("D'Vana Tendi", "USS Cerritos"),
                    new Assignment("Ellen Ripley", "Nostromo")
            ));

    @Mock
    private Gateway<AstroResponse> gateway;

    @InjectMocks
    private AstroService service;

    // Integration test -- no mocks
    @Test
    void testAstroData_RealGateway() {
        // Create an instance of AstroService using the real Gateway
        service = new AstroService(new AstroGateway());

        // Call the method under test
        Map<String, Long> astroData = service.getAstroData();

        // Print the results and check that they are reasonable
        astroData.forEach((craft, number) -> {
            System.out.println(number + " astronauts aboard " + craft);
            assertAll(
                    () -> assertThat(number).isGreaterThan(0),
                    () -> assertThat(craft).isNotBlank()
            );
        });
    }

    // Own mock class -- MockGateway
    @Test
    void testAstroData_OwnMockGateway() {
        // Create the service using the mock Gateway
        service = new AstroService(new MockGateway());

        // Call the method under test
        Map<String, Long> astroData = service.getAstroData();

        // Check the results from the method under test
        astroData.forEach((craft, number) -> {
            System.out.println(number + " astronauts aboard " + craft);
            assertAll(
                    () -> assertThat(number).isGreaterThan(0),
                    () -> assertThat(craft).isNotBlank()
            );
        });
    }

    // Unit test with mock Gateway using mock(Gateway.class)
    @SuppressWarnings("unchecked")
    @Test
    void testAstroData_MockGateway() {
        // 1. Create a mock Gateway
        Gateway<AstroResponse> mockGateway = mock(Gateway.class);

        // 2. Set expectations on the mock Gateway
        when(mockGateway.getResponse())
                .thenReturn(mockAstroResponse);

        // 3. Create an instance of AstroService using the mock Gateway
        AstroService service = new AstroService(mockGateway);

        // 4. Call the method under test
        Map<String, Long> astroData = service.getAstroData();

        // 5. Check the results from the method under test
        assertThat(astroData)
                .containsEntry("Babylon 5", 2L)
                .containsEntry("USS Cerritos", 4L)
                .containsEntry("Nostromo", 1L)
                .hasSize(3);
        astroData.forEach((craft, number) -> {
            System.out.println(number + " astronauts aboard " + craft);
            assertAll(
                    () -> assertThat(number).isGreaterThan(0),
                    () -> assertThat(craft).isNotBlank()
            );
        });

        // 6. Verify that the mock Gateway method was called
        verify(mockGateway).getResponse();
    }

    @Test
    void testAstroData_InjectedMockGateway() {
        // Mock Gateway created and injected into AstroService using
        //    @Mock and @InjectMock annotations
        //
        // Set the expectations on the mock
        when(gateway.getResponse())
                .thenReturn(mockAstroResponse);

        // Call the method under test
        Map<String, Long> astroData = service.getAstroData();

        // Check the results from the method under test
        assertThat(astroData)
                .containsEntry("Babylon 5", 2L)
                .containsEntry("Nostromo", 1L)
                .containsEntry("USS Cerritos", 4L);
        astroData.forEach((craft, number) -> {
            System.out.println(number + " astronauts aboard " + craft);
            assertAll(
                    () -> assertThat(number).isGreaterThan(0),
                    () -> assertThat(craft).isNotBlank()
            );
        });

        // Verify the stubbed method was called
        verify(gateway).getResponse();
    }

    // Unit test with injected mock Gateway (uses annotations)
    @Test
    void testAstroData_InjectedMockGatewayBDD() {
        // Mock Gateway created and injected into AstroService using
        //    @Mock and @InjectMock annotations
        //
        // Set the expectations on the mock
        given(gateway.getResponse())
                .willReturn(mockAstroResponse);

        // Call the method under test
        Map<String, Long> astroData = service.getAstroData();

        // Check the results from the method under test
        assertThat(astroData)
                .containsEntry("Babylon 5", 2L)
                .containsEntry("Nostromo", 1L)
                .containsEntry("USS Cerritos", 4L);
        astroData.forEach((craft, number) -> {
            System.out.println(number + " astronauts aboard " + craft);
            assertAll(
                    () -> assertThat(number).isGreaterThan(0),
                    () -> assertThat(craft).isNotBlank()
            );
        });

        // Verify the stubbed method was called
        then(gateway).should().getResponse();
    }

    // Check network failure
    @Test
    void testAstroData_failedGateway() {
        when(gateway.getResponse()).thenThrow(
                new RuntimeException(new IOException("Network problems")));

        Exception exception = assertThrows(
                RuntimeException.class,
                () -> service.getAstroData());

        Throwable cause = exception.getCause();
        assertAll(
                () -> assertEquals(IOException.class, cause.getClass()),
                () -> assertEquals("Network problems", cause.getMessage())
        );

        // verify:
        verify(gateway).getResponse();
    }

    // Check network failure
    @Test
    void testAstroData_failedGatewayBDD() {
        // given:
        willThrow(new RuntimeException(new IOException("Network problems")))
                .given(gateway).getResponse();

        // when:
        Exception exception = assertThrows(
                RuntimeException.class,
                () -> service.getAstroData());

        // then:
        Throwable cause = exception.getCause();
        assertAll(
                () -> assertEquals(IOException.class, cause.getClass()),
                () -> assertEquals("Network problems", cause.getMessage())
        );

        // verify:
        then(gateway).should().getResponse();
        then(gateway).shouldHaveNoMoreInteractions();
    }

}