package org.ak.billing.commands;

import org.ak.billing.beans.Product;
import org.ak.billing.beans.ShoppingCart;
import org.ak.billing.services.impls.MyCartService;

public class RemoveFromCartCommand implements Command {

    private final MyCartService cartService;
    private final Product product;
    private final ShoppingCart cart;

    public RemoveFromCartCommand(MyCartService cartService, Product product, ShoppingCart cart) {
        this.cartService = cartService;
        this.product = product;
        this.cart = cart;
    }

    @Override
    public void execute() {
        boolean removed = cartService.removeProduct(product.getId(), cart);
        if (removed) {
            System.out.println("==> [" + product.getName() + "] sepetinizden cikarildi!");
        } else {
            System.out.println("==> Urun sepetinizde bulunamadi.");
        }
    }

    @Override
    public void undo() {
        // Silme işlemini geri al, yani ürünü sepete geri ekle!
        cartService.addProduct(product, cart);
        System.out.println("GERI AL (UNDO) ==> [" + product.getName() + "] sepetinize GERI eklendi!");
    }
}
