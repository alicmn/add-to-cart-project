package org.ak.billing.observers;

import org.ak.billing.beans.Shopper;

public class SmsNotificationObserver implements InvoiceObserver {
    @Override
    public void onInvoiceGenerated(Shopper shopper) {
        String phone = shopper.getUserDetails().getContacts() != null
                && !shopper.getUserDetails().getContacts().isEmpty()
                        ? shopper.getUserDetails().getContacts().get(0)
                        : "Bilinmiyor";
        System.out.println("[SMS NOTIFICATION] => " + phone + " nolu telefona faturaniz SMS olarak yollanmistir.");
    }
}
