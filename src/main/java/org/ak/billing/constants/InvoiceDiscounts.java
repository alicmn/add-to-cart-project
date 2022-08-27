package org.ak.billing.constants;

public enum InvoiceDiscounts {
    NOT_PHONE(0.025),
    CUSTOMER(0.05),
    AFFILIATE(0.1),
    GOLD_CART(0.3),
    SILVER_CART(0.2);

    private final double discount;

    InvoiceDiscounts(double discount) {
        this.discount = discount;
    }

    public double getDiscount() {
        return discount;
    }
}
