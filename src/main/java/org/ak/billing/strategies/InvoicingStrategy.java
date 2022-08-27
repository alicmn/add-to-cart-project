package org.ak.billing.strategies;

import org.ak.billing.beans.Shopper;

public interface InvoicingStrategy {
    void generate(Shopper shopper);
}
