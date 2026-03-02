package org.ak.billing.strategies.impls;

import org.ak.billing.strategies.PaymentStrategy;
import java.math.BigDecimal;

public class CreditCardPaymentStrategy implements PaymentStrategy {
    private final String cardNumber;

    public CreditCardPaymentStrategy(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public void pay(BigDecimal amount) {
        System.out.println("==> Kredi Karti (" + cardNumber.substring(cardNumber.length() - 4) + ") ile " + amount
                + " ODENDI. Islem Bankaya aktarildi.");
    }
}
