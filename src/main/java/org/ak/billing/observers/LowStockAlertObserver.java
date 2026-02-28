package org.ak.billing.observers;

import org.ak.billing.beans.Product;
import org.ak.billing.constants.ApplicationConstants;

public class LowStockAlertObserver implements InventoryObserver {
    private static final int THRESHOLD = 5;

    @Override
    public void onProductStockChanged(Product product) {
        if (product.getQuantity() < THRESHOLD) {
            if ((Boolean) ApplicationConstants.SHOW_LOGS.getApplicationConstant()) {
                System.out.println("\n[SISTEM UYARISI - OBSERVER]: '" + product.getName()
                        + "' isimli urunun stogu kritik seviyeye (" +
                        product.getQuantity() + ") dustu! Acil tedarikciyi arayin.");
            }
        }
    }
}
