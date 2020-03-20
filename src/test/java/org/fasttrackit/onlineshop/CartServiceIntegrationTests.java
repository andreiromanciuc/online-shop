package org.fasttrackit.onlineshop;

import org.fasttrackit.onlineshop.domain.Customer;
import org.fasttrackit.onlineshop.service.CartService;
import org.fasttrackit.onlineshop.steps.CustomerTestSteps;
import org.fasttrackit.onlineshop.transfer.cart.AddProductsToCartRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class CartServiceIntegrationTests {

    @Autowired
    private CartService cartService;

    @Autowired
    private CustomerTestSteps customerTestSteps;

    @Test
    void addProductsToCart_whenNewCart_thenCartIsCreated() {
        Customer customer = customerTestSteps.createCustomer();

        AddProductsToCartRequest cartRequest = new AddProductsToCartRequest();
        cartRequest.setCustomerId(customer.getId());

        cartService.addProductsToCart(cartRequest);
    }


}
