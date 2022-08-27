package org.ak.billing.beans;

public class ShoppingCart {
    private Products productsInCart;

    public ShoppingCart() {
        productsInCart = new Products();
    }

    public Products getProductsInCart() {
        return productsInCart;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "productsInCart=" + productsInCart +
                '}';
    }
}
