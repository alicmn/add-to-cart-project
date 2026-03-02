package org.ak.billing.services;

import org.ak.billing.beans.Shopper;

public interface InvoiceService {
    void generate(Shopper shopper);

    void print(Shopper shopper);

    void addObserver(org.ak.billing.observers.InvoiceObserver observer);
}
