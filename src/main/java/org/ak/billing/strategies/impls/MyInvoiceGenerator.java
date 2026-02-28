package org.ak.billing.strategies.impls;

import org.ak.billing.beans.Invoice;
import org.ak.billing.beans.Product;
import org.ak.billing.beans.Shopper;
import org.ak.billing.beans.UserDetails;
import org.ak.billing.constants.ApplicationConstants;
import org.ak.billing.constants.InvoiceDiscounts;
import org.ak.billing.constants.ProductTypes;
import org.ak.billing.strategies.InvoicingStrategy;
import org.ak.billing.strategies.discount.UserDiscountFactory;
import org.ak.billing.strategies.discount.UserDiscountStrategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.UUID;

public class MyInvoiceGenerator implements InvoicingStrategy {
    @Override
    public void generate(Shopper shopper) {
        UserDetails userDetails = shopper.getUserDetails();

        UserDiscountStrategy discountStrategy = UserDiscountFactory.getStrategy(userDetails.getUserType());
        BigDecimal userDiscountPercentage = discountStrategy.calculateDiscount(userDetails.getUserSince());

        BigDecimal totalDiscountApplied = BigDecimal.ZERO;
        BigDecimal totalBillPhone = BigDecimal.ZERO;
        BigDecimal totalBillNonPhone = BigDecimal.ZERO;

        for (Product p : shopper.getShoppingCart().getProductsInCart().getProducts().values()) {
            BigDecimal discountedPrice = getDiscountedProductPrice(p);
            if (p.getType().equals(ProductTypes.PHONE)) {
                totalBillPhone = totalBillPhone.add(discountedPrice);
            } else {
                totalBillNonPhone = totalBillNonPhone.add(discountedPrice);
            }
            totalDiscountApplied = totalDiscountApplied.add(getDiscountOnProductPrice(p));
        }

        BigDecimal userDiscountAmount = totalBillNonPhone.multiply(userDiscountPercentage);
        totalDiscountApplied = totalDiscountApplied.add(userDiscountAmount);
        totalBillNonPhone = totalBillNonPhone.subtract(userDiscountAmount);

        BigDecimal totalBill = totalBillPhone.add(totalBillNonPhone);

        // Apply extra discount for every threshold amount on the bill
        BigDecimal threshold = (BigDecimal) ApplicationConstants.EXTRA_DISCOUNT_THRESHOLD.getApplicationConstant();
        BigDecimal extraDiscountAmount = (BigDecimal) ApplicationConstants.EXTRA_DISCOUNT_AMOUNT
                .getApplicationConstant();

        BigDecimal[] divisionResult = totalBill.divideAndRemainder(threshold);
        int extraDiscountSets = divisionResult[0].intValue();
        BigDecimal extraDiscount = extraDiscountAmount.multiply(new BigDecimal(extraDiscountSets));

        totalDiscountApplied = totalDiscountApplied.add(extraDiscount);
        totalBill = totalBill.subtract(extraDiscount);

        // Scale to 4 decimal places for consistency
        totalBill = totalBill.setScale(4, RoundingMode.HALF_UP);
        totalDiscountApplied = totalDiscountApplied.setScale(4, RoundingMode.HALF_UP);

        shopper.setInvoice(new Invoice(UUID.randomUUID(), LocalDateTime.now(), totalBill, totalDiscountApplied));
    }

    private BigDecimal getDiscountedProductPrice(Product product) {
        BigDecimal quantity = new BigDecimal(product.getQuantity());
        BigDecimal basePrice = product.getUnitPrice().multiply(quantity);
        return basePrice.subtract(getDiscountOnProductPrice(product));
    }

    private BigDecimal getDiscountOnProductPrice(Product product) {
        BigDecimal quantity = new BigDecimal(product.getQuantity());
        BigDecimal basePrice = product.getUnitPrice().multiply(quantity);
        BigDecimal discountRate = product.getType().equals(ProductTypes.PHONE) ? BigDecimal.ZERO
                : InvoiceDiscounts.NOT_PHONE.getDiscount();
        return basePrice.multiply(discountRate);
    }
}
