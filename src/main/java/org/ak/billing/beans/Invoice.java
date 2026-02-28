package org.ak.billing.beans;

import java.math.BigDecimal;

import java.time.LocalDateTime;
import java.util.UUID;

//Immutable
public final class Invoice {
    private final UUID uid;
    private final LocalDateTime date;
    private final BigDecimal amount;
    private final BigDecimal discount;

    public Invoice(UUID uid, LocalDateTime date, BigDecimal amount, BigDecimal discount) {
        this.uid = uid;
        this.date = date;
        this.amount = amount;
        this.discount = discount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public UUID getUid() {
        return uid;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "uid='" + uid + '\'' +
                ", date=" + date +
                ", amount=" + amount +
                ", discount=" + discount +
                '}';
    }
}
