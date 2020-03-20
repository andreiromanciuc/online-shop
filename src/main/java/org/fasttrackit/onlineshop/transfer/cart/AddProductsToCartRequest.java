package org.fasttrackit.onlineshop.transfer.cart;


import java.util.List;

public class AddProductsToCartRequest {
    private long customerId;
    private List<Long> productsIds;

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public List<Long> getProductsIds() {
        return productsIds;
    }

    public void setProductsIds(List<Long> productsIds) {
        this.productsIds = productsIds;
    }

    @Override
    public String toString() {
        return "AddProductsToCartRequest{" +
                "customerId=" + customerId +
                ", productsIds=" + productsIds +
                '}';
    }
}
