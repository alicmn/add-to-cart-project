package org.ak.billing.beans;

import org.ak.billing.constants.ProductTypes;

import java.math.BigDecimal;

import java.util.Objects;
import java.util.UUID;

public final class Product implements Cloneable {
    private final UUID id;
    private final String name;
    private int quantity;
    private final ProductTypes type;
    private final BigDecimal unitPrice;

    @Override
    public int hashCode() {
        int result = 1;
        int prime = 31;
        result = result * prime + ((id != null) ? (id.hashCode()) : (0));
        result = result * prime + ((name != null) ? (name.hashCode()) : (0));
        result = result * prime + ((type != null) ? (type.hashCode()) : (0));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        return Objects.equals(id, ((Product) obj).getId());
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Product(UUID id, String name, int quantity, ProductTypes type, BigDecimal unitPrice) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.type = type;
        this.unitPrice = unitPrice;
    }

    public UUID getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ProductTypes getType() {
        return type;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", type=" + type +
                ", unitPrice=" + unitPrice +
                '}';
    }

}
