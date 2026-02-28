package org.ak.billing.strategies.discount;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface UserDiscountStrategy {
    BigDecimal calculateDiscount(LocalDateTime userSince);
}
