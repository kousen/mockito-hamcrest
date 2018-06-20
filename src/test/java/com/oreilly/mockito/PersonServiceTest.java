package com.oreilly.mockito;

import com.oreilly.Person;
import com.oreilly.PersonRepository;
import com.oreilly.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class PersonServiceTest {

    @Mock
    private PersonRepository repository;

    @InjectMocks
    private PersonService service;

    private List<Person> people = Arrays.asList(
            new Person(1, "Grace", "Hopper",
                       LocalDate.of(1906, Month.DECEMBER, 9)),
            new Person(2, "Ada", "Lovelace",
                       LocalDate.of(1815, Month.DECEMBER, 10)),
            new Person(3, "Adele", "Goldberg",
                       LocalDate.of(1945, Month.JULY, 7)),
            new Person(4, "Anita", "Borg",
                       LocalDate.of(1949, Month.JANUARY, 17)),
            new Person(5, "Barbara", "Liskov",
                       LocalDate.of(1939, Month.NOVEMBER, 7)));

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        when(repository.findAll()).thenReturn(people);
        when(repository.count()).thenReturn((long) people.size());
    }

    @Test
    public void findMaxId() {
        assertThat(service.getHighestId(), is(5));
    }

    @Test
    public void getLastNames() {
        assertThat(service.getLastNames(),
                   containsInAnyOrder("Hopper", "Lovelace", "Goldberg",
                                      "Borg", "Liskov"));
    }

    @Test
    public void getTotalPeople() {
        assertThat(service.getTotalPeople(), is(equalTo((long) people.size())));
    }
}