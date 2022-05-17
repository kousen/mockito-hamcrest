package com.oreilly;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryPersonRepository implements PersonRepository {
    private static final List<Person> people = new ArrayList<>(List.of(
            new Person(1, "Grace", "Hopper", LocalDate.of(1906, Month.DECEMBER, 9)),
            new Person(2, "Ada", "Lovelace", LocalDate.of(1815, Month.DECEMBER, 10)),
            new Person(3, "Adele", "Goldberg", LocalDate.of(1945, Month.JULY, 7)),
            new Person(14, "Anita", "Borg", LocalDate.of(1949, Month.JANUARY, 17)),
            new Person(5, "Barbara", "Liskov", LocalDate.of(1939, Month.NOVEMBER, 7))
    ));

    @Override
    public Person save(Person person) {
        people.add(person);
        return person;
    }

    @Override
    public Optional<Person> findById(Integer id) {
        return people.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Person> findAll() {
        return people;
    }

    @Override
    public long count() {
        return people.size();
    }

    @Override
    public void delete(Person person) {
        people.remove(person);
    }
}
