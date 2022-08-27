package org.ak.billing.strategies;

import org.ak.billing.beans.Product;
import org.ak.billing.exceptions.InventoryShortageException;

import java.util.Set;

public interface CartLoadingStrategy {
    Set<Product> loadNEachFromInventory(Set<Product> inventory, int loadQuantity) throws InventoryShortageException;
}
