package org.ak.billing.daos;

import org.ak.billing.beans.Product;

import java.util.Set;
import java.util.UUID;

public interface StoreDao {
    boolean updateInventory(Product product);

    boolean updateInventoryBatch(Set<Product> products);

    Product getProduct(UUID pid);

    Set<Product> getAllProducts();
}
