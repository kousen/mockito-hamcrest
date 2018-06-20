package com.oreilly;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PersonService {
    private PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public List<String> getLastNames() {
        return repository.findAll().stream()
                .map(Person::getLast)
                .collect(Collectors.toList());
    }

    public Integer getHighestId() {
        return repository.findAll().stream()
                .mapToInt(Person::getId)
                .max().orElse(0);
    }

    public void savePeople(Person... person) {
        Arrays.stream(person)
              .forEach(repository::save);
    }

    public long getTotalPeople() {
        return repository.count();
    }
}
