package org.ak.billing;

import org.ak.billing.beans.Shopper;
import org.ak.billing.beans.ShoppingCart;
import org.ak.billing.beans.UserDetails;
import org.ak.billing.constants.ApplicationConstants;
import org.ak.billing.exceptions.InventoryShortageException;
import org.ak.billing.services.InvoiceService;
import org.ak.billing.services.StoreDBService;
import org.ak.billing.services.impls.MyCartService;

public class ShoppingApplication {

    private final StoreDBService myStoreDBService;
    private final MyCartService myCartService;
    private final InvoiceService myInvoiceService;

    public ShoppingApplication(StoreDBService myStoreDBService,
                               MyCartService myCartService,
                               InvoiceService myInvoiceService) {
        this.myStoreDBService = myStoreDBService;
        this.myCartService = myCartService;
        this.myInvoiceService = myInvoiceService;
    }

    public double shop(UserDetails userDetails) throws InventoryShortageException {

        ShoppingCart shoppingCart = myCartService.getNewShoppingCart();
        Shopper shopper = new Shopper(userDetails, shoppingCart);

        //Shopping Business Logic
        myCartService.loadNEachFromInventory(
                (int) ApplicationConstants.CART_QUANTITY.getApplicationConstant(),
                shoppingCart);

        myStoreDBService.updateInventory(myCartService.getAllProducts(shoppingCart));
        myInvoiceService.generate(shopper);
        myInvoiceService.print(shopper);
        return shopper.getInvoice().getAmount();
    }


}
