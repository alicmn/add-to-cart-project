package org.ak.billing.constants;

import java.math.BigDecimal;

public enum InvoiceDiscounts {
    NOT_PHONE(new BigDecimal("0.025")),
    CUSTOMER(new BigDecimal("0.05")),
    AFFILIATE(new BigDecimal("0.10")),
    GOLD_CART(new BigDecimal("0.30")),
    SILVER_CART(new BigDecimal("0.20"));

    private final BigDecimal discount;

    InvoiceDiscounts(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getDiscount() {
        return discount;
    }
}
