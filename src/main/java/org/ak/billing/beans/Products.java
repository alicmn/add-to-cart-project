package org.ak.billing.beans;

import java.util.LinkedHashMap;
import java.util.UUID;

public class Products {
    private LinkedHashMap<UUID, Product> products;

    public Products() {
        products = new LinkedHashMap<>();
    }

    public LinkedHashMap<UUID, Product> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return "Products{" +
                "products=" + products +
                '}';
    }
}
