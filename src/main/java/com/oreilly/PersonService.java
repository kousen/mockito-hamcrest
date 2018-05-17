package com.oreilly;

import java.util.List;
import java.util.stream.Collectors;

public class PersonService {
    private PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public List<String> getNames() {
        return repository.findAll().stream()
                .map(Person::getLast)
                .collect(Collectors.toList());
    }

    public Integer getHighestId() {
        return repository.findAll().stream()
                .mapToInt(Person::getId)
                .max().orElse(0);
    }

    public void savePerson(Person person) {
        repository.save(person);
    }
}
