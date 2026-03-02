package org.ak.billing.observers;

import org.ak.billing.beans.Shopper;
import java.math.BigDecimal;

public class EmailNotificationObserver implements InvoiceObserver {
    @Override
    public void onInvoiceGenerated(Shopper shopper) {
        BigDecimal total = shopper.getInvoice().getAmount();
        String email = shopper.getUserDetails().getContacts() != null
                && shopper.getUserDetails().getContacts().size() > 1
                        ? shopper.getUserDetails().getContacts().get(1)
                        : "Bilinmiyor";
        System.out.println("[EMAIL NOTIFICATION] => Faturaniz olusturuldu. Toplam tutar: $" + total
                + " Mail adresinize iletildi: " + email);
    }
}
