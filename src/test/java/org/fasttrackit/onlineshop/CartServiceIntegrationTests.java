package org.fasttrackit.onlineshop;

import org.fasttrackit.onlineshop.domain.Cart;
import org.fasttrackit.onlineshop.domain.Customer;
import org.fasttrackit.onlineshop.domain.Product;
import org.fasttrackit.onlineshop.service.CartService;
import org.fasttrackit.onlineshop.steps.CustomerTestSteps;
import org.fasttrackit.onlineshop.steps.ProductTestSteps;
import org.fasttrackit.onlineshop.transfer.cart.AddProductsToCartRequest;
import org.fasttrackit.onlineshop.transfer.cart.CartResponse;
import org.fasttrackit.onlineshop.transfer.cart.ProductInCartResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.Iterator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;


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

        CartResponse cart = cartService.getCart(customer.getId());
        assertThat(cart, notNullValue());
        assertThat(cart.getId(), is(customer.getId()));

        assertThat(cart.getProducts(), notNullValue());
        assertThat(cart.getProducts(), hasSize(1));

        Iterator<ProductInCartResponse> productIterator = cart.getProducts().iterator();

        assertThat(productIterator.hasNext(), is(true));

        ProductInCartResponse nextProduct = productIterator.next();
        assertThat(nextProduct, notNullValue());
        assertThat(nextProduct.getId(), is(product.getId()));
        assertThat(nextProduct.getName(), is(product.getName()));
        assertThat(nextProduct.getPrice(), is(product.getPrice()));

    }




}
