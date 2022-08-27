package org.ak.billing.services.impls;

import org.ak.billing.beans.Product;
import org.ak.billing.beans.Shopper;
import org.ak.billing.constants.ApplicationConstants;
import org.ak.billing.helpers.Utility;
import org.ak.billing.services.InvoiceService;
import org.ak.billing.strategies.InvoicingStrategy;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

public class MyInvoiceService implements InvoiceService {
    private final InvoicingStrategy invoicingStrategy;

    public MyInvoiceService(InvoicingStrategy invoicingStrategy) {
        this.invoicingStrategy = invoicingStrategy;
    }

    @Override
    public void generate(Shopper shopper) {
        invoicingStrategy.generate(shopper);
    }

    @Override
    public void print(Shopper shopper) {
        //Bill Header
        Utility.printBuffer();
        Utility.printCenter(ApplicationConstants.BILL_HEADER.getApplicationConstant().toString(),
                ApplicationConstants.BILL_PADDING.getApplicationConstant().toString());
        Utility.printBuffer();
        Utility.println(" ");

        //Bill & User Info
        Utility.printBillMeta(shopper);
        Utility.println(" ");

        //Bill Products Info
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
