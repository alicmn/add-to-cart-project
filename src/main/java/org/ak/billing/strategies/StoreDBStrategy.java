package org.ak.billing.strategies;

import org.ak.billing.beans.Products;

public interface StoreDBStrategy {
    ThreadLocal<Products> getProductInventory();
}
