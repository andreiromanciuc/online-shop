package org.fasttrackit.onlineshop.service;

import org.fasttrackit.onlineshop.domain.Cart;
import org.fasttrackit.onlineshop.domain.Customer;
import org.fasttrackit.onlineshop.domain.Product;
import org.fasttrackit.onlineshop.exception.ResourceNotfoundException;
import org.fasttrackit.onlineshop.persistance.CartRepository;
import org.fasttrackit.onlineshop.transfer.cart.AddProductsToCartRequest;
import org.fasttrackit.onlineshop.transfer.cart.CartResponse;
import org.fasttrackit.onlineshop.transfer.cart.ProductInCartResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Service
public class CartService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private final CartRepository cartRepository;
    private final CustomerService customerService;
    private final ProductService productService;

    @Autowired
    public CartService(CartRepository cartRepository, CustomerService customerService, ProductService productService) {
        this.cartRepository = cartRepository;
        this.customerService = customerService;
        this.productService = productService;
    }

    @Transactional
    public void addProductsToCart(AddProductsToCartRequest request) {
        LOGGER.info("Adding products to cart: {}", request);

        Cart cart = cartRepository.findById(request.getCustomerId()).orElse(new Cart());


        if (cart.getCustomer() == null) {
            Customer customer = customerService.getCustomer(request.getCustomerId());
            cart.setCustomer(customer);
        }

        for (Long id : request.getProductsIds()) {
            Product product = productService.findProduct(id);
            cart.addProductToCart(product);
        }

        cartRepository.save(cart);
    }

    // returning DTO to avoid lazy loading exceptions
    @Transactional
    public CartResponse getCart(long customerId) {
        LOGGER.info("Retrieving cart items for customer {}", customerId);
        Cart cart = cartRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotfoundException("Cart " + customerId + " does not exist."));

        CartResponse cartResponse = new CartResponse();
        cartResponse.setId(cart.getId());

        Set<ProductInCartResponse> productDtos = new HashSet<>();

        for (Product nextProduct : cart.getProducts()) {
            ProductInCartResponse productDto = new ProductInCartResponse();
            productDto.setId(nextProduct.getId());
            productDto.setName(nextProduct.getName());
            productDto.setPrice(nextProduct.getPrice());

            productDtos.add(productDto);
        }

        cartResponse.setProducts(productDtos);
        return cartResponse;
    }
}
