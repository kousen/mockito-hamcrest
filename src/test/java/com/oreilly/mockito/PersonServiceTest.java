package com.oreilly.mockito;

import com.oreilly.Person;
import com.oreilly.PersonRepository;
import com.oreilly.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PersonServiceTest {

    @Mock
    private PersonRepository repository;

    @InjectMocks
    private PersonService service;

    @Captor
    private ArgumentCaptor<Person> personArg;

    private final List<Person> people = Arrays.asList(
            new Person(1, "Grace", "Hopper", LocalDate.of(1906, Month.DECEMBER, 9)),
            new Person(2, "Ada", "Lovelace", LocalDate.of(1815, Month.DECEMBER, 10)),
            new Person(3, "Adele", "Goldberg", LocalDate.of(1945, Month.JULY, 7)),
            new Person(14, "Anita", "Borg", LocalDate.of(1949, Month.JANUARY, 17)),
            new Person(5, "Barbara", "Liskov", LocalDate.of(1939, Month.NOVEMBER, 7)));

    @Before
    public void init() {
        // MockitoAnnotations.initMocks(this);
        MockitoAnnotations.openMocks(this);

        when(repository.findAll())
                .thenReturn(people);
    }

    @Test
    public void findMaxId() {
        // assertThat(service.getHighestId(), is(14)); // Hamcrest matcher
        assertEquals(14, service.getHighestId());
    }

    @Test
    public void getLastNames() {
        assertThat(service.getLastNames(),
                containsInAnyOrder("Borg", "Goldberg", "Hopper",
                        "Liskov", "Lovelace"));
    }

    @Test
    public void getTotalPeople() {
        when(repository.count())
                .thenReturn((long) people.size());

        assertThat(service.getTotalPeople(), is(equalTo((long) people.size())));
    }

    @Test
    public void saveAllPeople() {
        when(repository.save(any(Person.class)))
                .thenReturn(people.get(0),
                        people.get(1),
                        people.get(2),
                        people.get(3),
                        people.get(4));

        // test the service (which uses the mock)
        assertThat(service.savePeople(people.get(0), people.get(1),
                people.get(2), people.get(3), people.get(4)),
                containsInAnyOrder(1, 2, 3, 14, 5));

        // verify the interaction between the service and the mock
        verify(repository, times(5)).save(any(Person.class));
        verify(repository, never()).delete(any(Person.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void useAnswer() {
        // Anonymous inner class
//        when(repository.save(any(Person.class)))
//                .thenAnswer(new Answer<Person>() {
//                    @Override
//                    public Person answer(InvocationOnMock invocation) throws Throwable {
//                        return invocation.getArgument(0);
//                    }
//                });

        // Lambda expression implementation of Answer<Person>
        when(repository.save(any(Person.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        List<Integer> ids = service.savePeople(people.toArray(new Person[0]));

        Integer[] actuals = people.stream()
                .map(Person::getId)
                .toArray(Integer[]::new);
        assertThat(ids, contains(actuals));
    }

    @Test(expected = RuntimeException.class)
    public void savePersonThrowsException() {
        when(repository.save(any(Person.class)))
                .thenThrow(RuntimeException.class);

        service.savePeople(people.get(0));
    }

    @Test
    public void createPerson() {
        Person hopper = people.get(0);

        Person person = service.createPerson(hopper.getId(),
                hopper.getFirst(),
                hopper.getLast(),
                hopper.getDob());

        verify(repository).save(personArg.capture());
        assertThat(personArg.getValue(), is(hopper));  // verifies the local variable
        assertThat(person, is(hopper));  // verifies the return
    }

    @Test
    public void deleteAll() {
        // Not necessary, but works
        doNothing().when(repository).delete(any(Person.class));

        service.deleteAll();

        verify(repository, times(5)).delete(any(Person.class));
    }

    @Test
    public void findByIdThatDoesNotExist() {
        // General case
        // when(repository.findById(anyInt())).thenReturn(Optional.empty());

        // More specific, custom matcher
        when(repository.findById(argThat(id -> id > 14))).thenReturn(Optional.empty());

        List<Person> personList = service.findByIds(999);
        assertThat(personList, is(emptyCollectionOf(Person.class)));
    }

    @Test
    public void findByIdsThatDoExist() {
        when(repository.findById(anyInt()))
                .thenAnswer(invocation -> people.stream()
                        .filter(person ->
                                invocation.getArgument(0).equals(person.getId()))
                        .findFirst());

        List<Person> personList = service.findByIds(1, 3, 5);
        assertThat(personList, contains(people.get(0),
                people.get(2),
                people.get(4)));
    }
}