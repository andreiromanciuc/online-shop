package org.fasttrackit.onlineshop.steps;

import org.fasttrackit.onlineshop.domain.Customer;
import org.fasttrackit.onlineshop.service.CartService;
import org.fasttrackit.onlineshop.service.CustomerService;
import org.fasttrackit.onlineshop.transfer.customer.SaveCustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;

@Component
public class CustomerTestSteps {

    @Autowired
    private CustomerService customerService;

    public Customer createCustomer() {
        SaveCustomerRequest request = new SaveCustomerRequest();
        request.setFirstName("Andrei");
        request.setLastName("Romanciuc");

        Customer customer = customerService.createCustomer(request);

        assertThat(customer, notNullValue());
        assertThat(customer.getId(), greaterThan(0L));
        assertThat(customer.getFirstName(), is(request.getFirstName()));
        assertThat(customer.getLastName(), is(request.getLastName()));

        return customer;
    }
}
