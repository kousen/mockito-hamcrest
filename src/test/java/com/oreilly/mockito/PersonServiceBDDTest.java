package com.oreilly.mockito;

import com.oreilly.Person;
import com.oreilly.PersonRepository;
import com.oreilly.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PersonServiceBDDTest {
    @Mock
    private PersonRepository repository;

    @InjectMocks
    private PersonService service;

    private final List<Person> people = Arrays.asList(
            new Person(1, "Grace", "Hopper", LocalDate.of(1906, Month.DECEMBER, 9)),
            new Person(2, "Ada", "Lovelace", LocalDate.of(1815, Month.DECEMBER, 10)),
            new Person(3, "Adele", "Goldberg", LocalDate.of(1945, Month.JULY, 7)),
            new Person(4, "Anita", "Borg", LocalDate.of(1949, Month.JANUARY, 17)),
            new Person(5, "Barbara", "Liskov", LocalDate.of(1939, Month.NOVEMBER, 7)));

    @Before
    public void init() {
        given(repository.findAll())
                .willReturn(people);
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
        given(repository.count())
                .willReturn((long) people.size());

        assertThat(service.getTotalPeople(), is(equalTo((long) people.size())));
    }

    @Test
    public void saveAllPeople() {
        given(repository.save(any(Person.class)))
                .willReturn(people.get(0),
                            people.get(1),
                            people.get(2),
                            people.get(3),
                            people.get(4));

        List<Integer> ids = service.savePeople(people.toArray(new Person[0]));

        assertThat(ids, containsInAnyOrder(1, 2, 3, 4, 5));

        then(repository)
                .should(times(5))
                .save(any(Person.class));
        then(repository)
                .should(never())
                .delete(any(Person.class));
    }

    public void useAnswer() {
        given(repository.save(any(Person.class)))
                .will(invocation -> invocation.getArgument(0));

        List<Integer> ids = service.savePeople(people.toArray(new Person[0]));

        Integer[] actuals = people.stream()
                                  .map(Person::getId)
                                  .toArray(Integer[]::new);
        assertThat(ids, contains(actuals));
    }

    @Test(expected = RuntimeException.class)
    public void savePersonThrowsException() {
        given(repository.save(any(Person.class)))
                .willThrow(RuntimeException.class);

        service.savePeople(people.get(0));
    }

}
