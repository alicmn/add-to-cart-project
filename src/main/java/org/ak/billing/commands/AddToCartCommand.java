package org.ak.billing.commands;

import org.ak.billing.beans.Product;
import org.ak.billing.beans.ShoppingCart;
import org.ak.billing.services.impls.MyCartService;

public class AddToCartCommand implements Command {

    private final MyCartService cartService;
    private final Product product;
    private final ShoppingCart cart;

    public AddToCartCommand(MyCartService cartService, Product product, ShoppingCart cart) {
        this.cartService = cartService;
        this.product = product;
        this.cart = cart;
    }

    @Override
    public void execute() {
        boolean added = cartService.addProduct(product, cart);
        if (added) {
            System.out
                    .println("==> [" + product.getName() + "] sepetinize " + product.getQuantity() + " adet eklendi!");
        } else {
            System.out.println("==> Urun zaten sepette olabilir veya eklenemedi.");
        }
    }

    @Override
    public void undo() {
        // Sepetten bu ürünü bulup tamamen silmek (veya miktarını düşürmek)
        // Basitlik için ürün tamamen sepetten çıkarılır
        cartService.getAllProducts(cart).remove(product);
        System.out.println("GERI AL (UNDO) ==> [" + product.getName() + "] sepetten cikarildi!");
    }
}
