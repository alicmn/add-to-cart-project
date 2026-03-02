package org.ak.billing.observers;

import org.ak.billing.beans.Shopper;

public interface InvoiceObserver {
    void onInvoiceGenerated(Shopper shopper);
}
