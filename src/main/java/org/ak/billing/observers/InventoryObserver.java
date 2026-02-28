package org.ak.billing.observers;

import org.ak.billing.beans.Product;

public interface InventoryObserver {
    void onProductStockChanged(Product product);
}
