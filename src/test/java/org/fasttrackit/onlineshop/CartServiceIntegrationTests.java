package org.fasttrackit.onlineshop;

import org.fasttrackit.onlineshop.domain.Customer;
import org.fasttrackit.onlineshop.domain.Product;
import org.fasttrackit.onlineshop.service.CartService;
import org.fasttrackit.onlineshop.steps.CustomerTestSteps;
import org.fasttrackit.onlineshop.steps.ProductTestSteps;
import org.fasttrackit.onlineshop.transfer.cart.AddProductsToCartRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;


@SpringBootTest
public class CartServiceIntegrationTests {

    @Autowired
    private CartService cartService;
    @Autowired
    private ProductTestSteps productTestSteps;

    @Autowired
    private CustomerTestSteps customerTestSteps;

    @Test
    void addProductsToCart_whenNewCart_thenCartIsCreated() {
        Customer customer = customerTestSteps.createCustomer();
        Product product = productTestSteps.createProduct();

        AddProductsToCartRequest cartRequest = new AddProductsToCartRequest();
        cartRequest.setCustomerId(customer.getId());
        cartRequest.setProductsIds(Collections.singletonList(product.getId()));

        cartService.addProductsToCart(cartRequest);
    }


}
