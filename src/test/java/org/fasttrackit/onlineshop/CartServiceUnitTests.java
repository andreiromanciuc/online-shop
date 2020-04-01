package org.fasttrackit.onlineshop;

import org.fasttrackit.onlineshop.domain.Cart;
import org.fasttrackit.onlineshop.domain.Customer;
import org.fasttrackit.onlineshop.domain.Product;
import org.fasttrackit.onlineshop.persistance.CartRepository;
import org.fasttrackit.onlineshop.service.CartService;
import org.fasttrackit.onlineshop.service.CustomerService;
import org.fasttrackit.onlineshop.service.ProductService;
import org.fasttrackit.onlineshop.transfer.cart.AddProductsToCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CartServiceUnitTests {

    private CartService cartService;

    @Mock
    private CartRepository cartRepository;
    @Mock
    private CustomerService customerService;
    @Mock
    private ProductService productService;

    @Before
    public void setup() {
        cartService = new CartService(cartRepository, customerService, productService);
    }

    @Test
    public void addProductsToCart_whenNewCart_thenNoErrorIsThrown() {
        when(cartRepository.findById(anyLong())).thenReturn(Optional.empty());

        Customer customer = new Customer();
        customer.setId(1);
        customer.setFirstName("FirstName");
        customer.setLastName("LastName");

        when(customerService.getCustomer((anyLong()))).thenReturn(customer);

        Product product = new Product();
        product.setId(2);
        product.setName("test product");
        product.setDescription("test description");
        product.setPrice(30);
        product.setQuantity(100);

        when(productService.findProduct(anyLong())).thenReturn(product);

        when(cartRepository.save(any(Cart.class))).thenReturn(null);

        AddProductsToCartRequest request = new AddProductsToCartRequest();
        request.setProductsIds(Collections.singletonList(product.getId()));
        request.setCustomerId(customer.getId());

        cartService.addProductsToCart(request);

        verify(cartRepository).findById(anyLong());
        verify(customerService).getCustomer(anyLong());
        verify(productService).findProduct(anyLong());
        verify(cartRepository).save(any(Cart.class));

    }

}
