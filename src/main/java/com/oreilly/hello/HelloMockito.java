package com.oreilly.hello;

import java.util.Optional;

public class HelloMockito {
    private String greeting = "Hello, %s, from Mockito!";
    private final PersonRepository personRepository;
    private final TranslationService translationService;

    public HelloMockito(PersonRepository personRepository, TranslationService translationService) {
        this.personRepository = personRepository;
        this.translationService = translationService;
    }

    public String greet(int id, String sourceLanguage, String targetLanguage) {
        Optional<Person> person = personRepository.findById(id);
        String name = person.map(Person::getFirst).orElse("World");
        return translationService.translate(String.format(greeting, name),
                sourceLanguage, targetLanguage);
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public String getGreeting() {
        return greeting;
    }
}
