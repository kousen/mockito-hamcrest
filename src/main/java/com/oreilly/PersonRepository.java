package com.oreilly;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
public interface PersonRepository {
    Person save(Person person);
    Optional<Person> findById(Integer id);
    List<Person> findAll();
    long count();
    void delete(Person person);
}
