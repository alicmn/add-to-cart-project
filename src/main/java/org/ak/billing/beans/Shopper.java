package org.ak.billing.beans;

public final class Shopper {
    private final UserDetails userDetails;
    private final ShoppingCart shoppingCart;
    private Invoice invoice;

    public Shopper(UserDetails userDetails, ShoppingCart shoppingCart) {
        this.userDetails = userDetails;
        this.shoppingCart = shoppingCart;
        this.invoice = null;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    @Override
    public String toString() {
        return "Shopper{" +
                "userDetails=" + userDetails +
                ", shoppingCart=" + shoppingCart +
                ", invoice=" + invoice +
                '}';
    }
}
