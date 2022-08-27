package org.ak.billing.services;

import org.ak.billing.beans.Product;

import java.util.Set;
import java.util.UUID;

public interface StoreDBService {
    Set<Product> getInventory();

    boolean isTransactionAllowed(UUID pid, int quantity);

    void updateInventory(Set<Product> cartProducts);
}
