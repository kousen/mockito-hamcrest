package com.oreilly.hello;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HelloMockitoTest {
    @Mock
    private PersonRepository repository;

    @Mock
    private TranslationService translationService;

    @InjectMocks
    private HelloMockito helloMockito;

    @Test @DisplayName("Greet Admiral Hopper")
    void greetForPersonThatExists() {
        // Set the expectations
        when(repository.findById(anyInt()))
                .thenReturn(Optional.of(new Person(1, "Grace", "Hopper", LocalDate.now())));
        when(translationService.translate("Hello, Grace, from Mockito!", "en", "en"))
                .thenReturn("Hello, Grace, from Mockito!");

        // Call the method under test
        String greeting = helloMockito.greet(1, "en", "en");
        assertEquals("Hello, Grace, from Mockito!", greeting);
        assertThat(greeting).isEqualTo("Hello, Grace, from Mockito!");

        InOrder inOrder = inOrder(repository, translationService);

        inOrder.verify(repository).findById(anyInt());
        inOrder.verify(translationService).translate(anyString(), eq("en"), eq("en"));
        // inOrder.verify(translationService).translate(anyString(), "en", "en");
    }

    @Test @DisplayName("Greet a person not in the database")
    void greetForPersonThatDoesNotExist() {
        when(repository.findById(anyInt()))
                .thenReturn(Optional.empty());
        when(translationService.translate("Hello, World, from Mockito!", "en", "en"))
                .thenReturn("Hello, World, from Mockito!");

        String greeting = helloMockito.greet(100, "en", "en");
        assertEquals("Hello, World, from Mockito!", greeting);

        InOrder inOrder = inOrder(repository, translationService);

        inOrder.verify(repository).findById(anyInt());
        inOrder.verify(translationService).translate(anyString(), eq("en"), eq("en"));
    }

    @Test
    void testGetterAndSetter() {
        assertThat(helloMockito.getGreeting()).isNotNull();
        assertThat(helloMockito.getGreeting()).isEqualTo("Hello, %s, from Mockito!");

        helloMockito.setGreeting("Hi there, %s, from Mockito!");
        assertThat(helloMockito.getGreeting()).isEqualTo("Hi there, %s, from Mockito!");
    }
}