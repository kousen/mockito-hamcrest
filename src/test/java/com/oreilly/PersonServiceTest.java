package com.oreilly;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

public class PersonServiceTest {

    @Mock
    private PersonRepository repository;

    @InjectMocks
    private PersonService service;

    @Before
    private void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findMaxId() {

        Integer id = service.getHighestId();
    }
}