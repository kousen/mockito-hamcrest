package com.oreilly;

import java.util.List;
import java.util.Optional;

public interface PersonRepository {
    Person save(Person person);
    Optional<Person> findById(Integer id);
    List<Person> findAll();
    long count();
    void delete(Person person);
    boolean existsById(Integer id);
}
