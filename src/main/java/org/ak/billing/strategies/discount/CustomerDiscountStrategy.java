package org.ak.billing.strategies.discount;

import org.ak.billing.constants.InvoiceDiscounts;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class CustomerDiscountStrategy implements UserDiscountStrategy {
    @Override
    public BigDecimal calculateDiscount(LocalDateTime userSince) {
        if (ChronoUnit.YEARS.between(userSince, LocalDateTime.now()) > 2) {
            return InvoiceDiscounts.CUSTOMER.getDiscount();
        }
        return BigDecimal.ZERO;
    }
}
