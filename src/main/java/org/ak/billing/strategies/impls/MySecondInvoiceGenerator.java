package org.ak.billing.strategies.impls;

import org.ak.billing.beans.Invoice;
import org.ak.billing.beans.Product;
import org.ak.billing.beans.Shopper;
import org.ak.billing.beans.UserDetails;
import org.ak.billing.constants.InvoiceDiscounts;
import org.ak.billing.constants.ProductTypes;
import org.ak.billing.constants.UserTypes;
import org.ak.billing.strategies.InvoicingStrategy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class MySecondInvoiceGenerator implements InvoicingStrategy {

    @Override
    public void generate(Shopper shopper) {
        UserDetails userDetails = shopper.getUserDetails();
        double userDiscountPercentage = getUserDiscount(userDetails.getUserType(), userDetails.getUserSince());
        double totalDiscountApplied = 0.0d;
        double totalBill = 0.0d;

        for (Product p : shopper.getShoppingCart().getProductsInCart().getProducts().values()) {
            totalBill += getDiscountedProductPrice(p);
            totalDiscountApplied += getDiscountOnProductPrice(p);
        }

        totalDiscountApplied += totalBill * userDiscountPercentage;
        totalBill *= (1 - userDiscountPercentage);

        shopper.setInvoice(new Invoice(UUID.randomUUID(), LocalDateTime.now(), BigDecimal.valueOf(totalBill),
                BigDecimal.valueOf(totalDiscountApplied)));
    }

    private double getDiscountedProductPrice(Product product) {
        return product.getUnitPrice().multiply(BigDecimal.valueOf(product.getQuantity())).doubleValue()
                - getDiscountOnProductPrice(product);
    }

    private double getDiscountOnProductPrice(Product product) {
        return product.getUnitPrice().multiply(BigDecimal.valueOf(product.getQuantity())).doubleValue() *
                ((product.getType().equals(ProductTypes.PHONE)) ? 0
                        : InvoiceDiscounts.NOT_PHONE.getDiscount().doubleValue());
    }

    private double getUserDiscount(UserTypes userType, LocalDateTime userSince) {
        double userDiscountPercentage = 0.0d;
        switch (userType) {
            case AFFILIATE:
                userDiscountPercentage = InvoiceDiscounts.AFFILIATE.getDiscount().doubleValue();
                break;
            case CUSTOMER:
                if (ChronoUnit.YEARS.between(userSince, LocalDateTime.now()) > 2) {
                    userDiscountPercentage = InvoiceDiscounts.CUSTOMER.getDiscount().doubleValue();
                }
                break;
            case GOLD_CART:
                userDiscountPercentage = InvoiceDiscounts.GOLD_CART.getDiscount().doubleValue();
                break;
            case SILVER_CART:
                userDiscountPercentage = InvoiceDiscounts.SILVER_CART.getDiscount().doubleValue();
                break;
        }
        return userDiscountPercentage;
    }
}
