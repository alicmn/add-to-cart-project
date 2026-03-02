package org.ak.billing.strategies.impls;

import org.ak.billing.strategies.PaymentStrategy;
import java.math.BigDecimal;

public class CashOnDeliveryPaymentStrategy implements PaymentStrategy {
    @Override
    public void pay(BigDecimal amount) {
        System.out
                .println("==> Kapi Odemesi Secildi. Kurye urunleri teslim ederken " + amount + " nakit tahsil edecek.");
    }
}
