package org.ak.billing.strategies.impls;

import org.ak.billing.beans.Invoice;
import org.ak.billing.beans.Product;
import org.ak.billing.beans.Shopper;
import org.ak.billing.beans.UserDetails;
import org.ak.billing.constants.ApplicationConstants;
import org.ak.billing.constants.InvoiceDiscounts;
import org.ak.billing.constants.ProductTypes;
import org.ak.billing.constants.UserTypes;
import org.ak.billing.strategies.InvoicingStrategy;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class MyInvoiceGenerator implements InvoicingStrategy {
    @Override
    public void generate(Shopper shopper) {
        UserDetails userDetails = shopper.getUserDetails();
        double userDiscountPercentage = getUserDiscount(userDetails.getUserType(), userDetails.getUserSince());
        double totalDiscountApplied = 0.0d;
        double totalBillPhone = 0.0d;
        double totalBillNonPhone = 0.0d;

        for (Product p : shopper.getShoppingCart().getProductsInCart().getProducts().values()) {
            double discountedPrice = getDiscountedProductPrice(p);
            if (p.getType().equals(ProductTypes.PHONE)) {
                totalBillPhone += discountedPrice;
            } else {
                totalBillNonPhone += discountedPrice;
            }
            totalDiscountApplied += getDiscountOnProductPrice(p);
        }

        double userDiscountAmount = totalBillNonPhone * userDiscountPercentage;
        totalDiscountApplied += userDiscountAmount;
        totalBillNonPhone -= userDiscountAmount;

        double totalBill = totalBillPhone + totalBillNonPhone;

        // Apply extra discount for every threshold amount on the bill
        double threshold = (double) ApplicationConstants.EXTRA_DISCOUNT_THRESHOLD.getApplicationConstant();
        double extraDiscountAmount = (double) ApplicationConstants.EXTRA_DISCOUNT_AMOUNT.getApplicationConstant();

        int extraDiscountSets = (int) (totalBill / threshold);
        double extraDiscount = extraDiscountSets * extraDiscountAmount;

        totalDiscountApplied += extraDiscount;
        totalBill -= extraDiscount;

        shopper.setInvoice(new Invoice(UUID.randomUUID(), LocalDateTime.now(), totalBill, totalDiscountApplied));
    }

    private double getDiscountedProductPrice(Product product) {
        return product.getQuantity() * product.getUnitPrice() - getDiscountOnProductPrice(product);
    }

    private double getDiscountOnProductPrice(Product product) {
        return product.getQuantity() * product.getUnitPrice() *
                ((product.getType().equals(ProductTypes.PHONE)) ? 0 : InvoiceDiscounts.NOT_PHONE.getDiscount());
    }

    private double getUserDiscount(UserTypes userType, LocalDateTime userSince) {
        double userDiscountPercentage = 0.0d;
        switch (userType) {
            case AFFILIATE:
                userDiscountPercentage = InvoiceDiscounts.AFFILIATE.getDiscount();
                break;
            case CUSTOMER:
                if (ChronoUnit.YEARS.between(userSince, LocalDateTime.now()) > 2) {
                    userDiscountPercentage = InvoiceDiscounts.CUSTOMER.getDiscount();
                }
                break;
            case GOLD_CART:
                userDiscountPercentage = InvoiceDiscounts.GOLD_CART.getDiscount();
                break;
            case SILVER_CART:
                userDiscountPercentage = InvoiceDiscounts.SILVER_CART.getDiscount();
                break;
        }
        return userDiscountPercentage;
    }
}
