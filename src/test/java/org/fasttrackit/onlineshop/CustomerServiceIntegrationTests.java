package org.fasttrackit.onlineshop;

import org.fasttrackit.onlineshop.domain.Customer;
import org.fasttrackit.onlineshop.exception.ResourceNotfoundException;
import org.fasttrackit.onlineshop.service.CustomerService;
import org.fasttrackit.onlineshop.transfer.customer.SaveCustomerRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.TransactionSystemException;

import javax.validation.ConstraintViolationException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;

@SpringBootTest
public class CustomerServiceIntegrationTests {

    @Autowired
    private CustomerService customerService;

    @Test
    void createCustomer_whenCustomerExist_thenReturnCustomer() {
        createCustomer();
    }

    @Test
    void createCustomer_whenMissingName_thenThrowException() {
        SaveCustomerRequest request = new SaveCustomerRequest();
        request.setLastName("Romanciuc");

        try {
            customerService.createCustomer(request);
        } catch (Exception e) {
            assertThat(e, notNullValue());
            assertThat("Unexpected exception time", e instanceof ConstraintViolationException);
        }
    }

    @Test
    void getCustomer_whenCustomerExist_thenReturnCustomer() {
        Customer customer = createCustomer();

        Customer response = customerService.getCustomer(customer.getId());

        assertThat(response, notNullValue());
        assertThat(response.getId(), is(customer.getId()));
        assertThat(response.getFirstName(), is(customer.getFirstName()));
        assertThat(response.getLastName(), is(customer.getLastName()));
    }

    @Test
    void getCustomer_whenNonExistingCustomer_thenThrowResourceNotFoundExceptionError() {
        Assertions.assertThrows(ResourceNotfoundException.class, ()->customerService.getCustomer(99999999));
    }

    @Test
    void updateCustomer_whenValidRequest_thenReturnUpdatedCustomer() {
        Customer customer = createCustomer();

        SaveCustomerRequest request = new SaveCustomerRequest();
        request.setFirstName(customer.getFirstName() + "updated");
        request.setLastName(customer.getLastName() + "updated");

        Customer updatedCustomer = customerService.updateCustomer(customer.getId(), request);

        assertThat(updatedCustomer, notNullValue());
        assertThat(updatedCustomer.getId(), is(customer.getId()));
        assertThat(updatedCustomer.getFirstName(), is(request.getFirstName()));
        assertThat(updatedCustomer.getLastName(), is(request.getLastName()));
    }

    @Test
    void deletingCustomer_whenCustomerExist_thenNonExistingAnymore() {
        Customer customer = createCustomer();
        customerService.deleteCustomer(customer.getId());
        Assertions.assertThrows(ResourceNotfoundException.class, ()-> customerService.getCustomer(customer.getId()));

    }

    private Customer createCustomer() {
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
