package org.ak.billing.beans;

import java.time.LocalDateTime;
import java.util.UUID;

//Immutable
public final class Invoice {
    private final UUID uid;
    private final LocalDateTime date;
    private final double amount;
    private final double discount;

    public Invoice(UUID uid, LocalDateTime date, double amount, double discount) {
        this.uid = uid;
        this.date = date;
        this.amount = amount;
        this.discount = discount;
    }

    public double getAmount() {
        return amount;
    }

    public double getDiscount() {
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
