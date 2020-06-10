package com.oreilly.hamcrest;

import com.oreilly.Customer;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CustomerTest {
    @Test
    public void orderCustomerByName() {
        Customer c1 = new Customer("Fred");
        Customer c2 = new Customer("Barney");

        assertThat(c1, is(lessThan(c2)));
    }
}