package org.ak.billing.daos.impls;

import org.ak.billing.beans.Product;
import org.ak.billing.daos.StoreDao;
import org.ak.billing.strategies.StoreDBStrategy;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

public class MyStoreDao implements StoreDao {

    StoreDBStrategy myStore;

    public MyStoreDao(StoreDBStrategy myStore) {
        this.myStore = myStore;
    }

    @Override
    public boolean updateInventory(Product product) {
        boolean response = false;
        if (myStore.getProductInventory().get().getProducts().containsKey(product.getId())) {
            myStore.getProductInventory().get().getProducts().put(product.getId(), product);
            response = true;
        }
        return response;
    }

    @Override
    public boolean updateInventoryBatch(Set<Product> products) {
        boolean response = false;
        for (Product p : products) {
            updateInventory(p);
        }
        return response;
    }

    @Override
    public Product getProduct(UUID pid) {
        Product product = null;
        if (myStore.getProductInventory().get().getProducts().containsKey(pid)) {
            product = myStore.getProductInventory().get().getProducts().get(pid);
        }
        return product;
    }

    @Override
    public Set<Product> getAllProducts() {
        return new LinkedHashSet<>(myStore.getProductInventory().get().getProducts().values());
    }
}
