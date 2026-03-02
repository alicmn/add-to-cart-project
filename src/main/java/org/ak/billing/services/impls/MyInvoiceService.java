package org.ak.billing.services.impls;

import org.ak.billing.beans.Shopper;
import org.ak.billing.constants.ApplicationConstants;
import org.ak.billing.helpers.Utility;
import org.ak.billing.services.InvoiceService;
import org.ak.billing.strategies.InvoicingStrategy;

public class MyInvoiceService implements InvoiceService {
    private final InvoicingStrategy invoicingStrategy;
    private final java.util.List<org.ak.billing.observers.InvoiceObserver> observers = new java.util.ArrayList<>();

    public MyInvoiceService(InvoicingStrategy invoicingStrategy) {
        this.invoicingStrategy = invoicingStrategy;
    }

    @Override
    public void addObserver(org.ak.billing.observers.InvoiceObserver observer) {
        observers.add(observer);
    }

    @Override
    public void generate(Shopper shopper) {
        invoicingStrategy.generate(shopper);
        // Faturasi uretildiginde tum dinleyenleri/aboneleri (observers) uyar
        for (org.ak.billing.observers.InvoiceObserver observer : observers) {
            observer.onInvoiceGenerated(shopper);
        }
    }

    @Override
    public void print(Shopper shopper) {
        // Bill Header
        Utility.printBuffer();
        Utility.printCenter(ApplicationConstants.BILL_HEADER.getApplicationConstant().toString(),
                ApplicationConstants.BILL_PADDING.getApplicationConstant().toString());
        Utility.printBuffer();
        Utility.println(" ");

        // Bill & User Info
        Utility.printBillMeta(shopper);
        Utility.println(" ");

        // Bill Products Info
        Utility.printBuffer();
        Utility.printCenter(ApplicationConstants.BILL_PRODUCT_HEADER.getApplicationConstant().toString(),
                ApplicationConstants.BILL_PADDING.getApplicationConstant().toString());
        Utility.printProducts(shopper.getShoppingCart().getProductsInCart().getProducts().values());
        Utility.printColumn("Total Discount = $" + ApplicationConstants.df.format(shopper.getInvoice().getDiscount()),
                "Discounted Bill = $" + ApplicationConstants.df.format(shopper.getInvoice().getAmount()),
                ApplicationConstants.BILL_SPACE.getApplicationConstant().toString());
        Utility.printBuffer();
    }

}
