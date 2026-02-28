package org.ak.billing.helpers;

import org.ak.billing.beans.Product;
import org.ak.billing.beans.Shopper;
import org.ak.billing.constants.ApplicationConstants;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

public class Utility {

        public static void println(Object o) {
                if ((Boolean) ApplicationConstants.SHOW_LOGS.getApplicationConstant()) {
                        System.out.println(o);
                }
        }

        public static String getFormattedDate(LocalDateTime localDateTime) {
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(
                                (String) ApplicationConstants.DATE_TIME_FORMAT.getApplicationConstant());
                return localDateTime.format(dateTimeFormatter);
        }

        public static String getCSVFromList(List list) {
                String csv = "";
                if (list != null && !list.isEmpty()) {
                        for (Object object : list) {
                                csv += object.toString() + ", ";
                        }
                        csv = csv.substring(0, csv.lastIndexOf(", "));
                }
                return csv;
        }

        public static void printBillMeta(Shopper shopper) {
                printColumn("Date", Utility.getFormattedDate(shopper.getInvoice().getDate()),
                                ApplicationConstants.BILL_SPACE.getApplicationConstant().toString());
                printColumn("Bill ID", shopper.getInvoice().getUid().toString(),
                                ApplicationConstants.BILL_SPACE.getApplicationConstant().toString());
                printColumn("Customer Name", shopper.getUserDetails().getName(),
                                ApplicationConstants.BILL_SPACE.getApplicationConstant().toString());
                printColumn("Customer ID", shopper.getUserDetails().getUid(),
                                ApplicationConstants.BILL_SPACE.getApplicationConstant().toString());
                printColumn("Customer Contacts", Utility.getCSVFromList(shopper.getUserDetails().getContacts()),
                                ApplicationConstants.BILL_SPACE.getApplicationConstant().toString());
                printColumn("Customer Since", Utility.getFormattedDate(shopper.getUserDetails().getUserSince()),
                                ApplicationConstants.BILL_SPACE.getApplicationConstant().toString());
                printColumn("Customer Type", shopper.getUserDetails().getUserType().toString(),
                                ApplicationConstants.BILL_SPACE.getApplicationConstant().toString());
        }

        public static void printBuffer() {
                Utility.println(StringUtils.repeat(
                                ApplicationConstants.BILL_PADDING.getApplicationConstant().toString(),
                                (int) ApplicationConstants.BILL_LENGTH.getApplicationConstant()));
        }

        public static void printCenter(String s, String padding) {
                Utility.println(StringUtils.rightPad(StringUtils.leftPad(s,
                                ((int) ApplicationConstants.BILL_LENGTH.getApplicationConstant() + s.length()) / 2,
                                padding), (int) ApplicationConstants.BILL_LENGTH.getApplicationConstant(), padding));
        }

        public static void printColumn(String s, String t, String padding) {
                Utility.println(
                                StringUtils.repeat(ApplicationConstants.BILL_SPACE.getApplicationConstant().toString(),
                                                (int) ApplicationConstants.BILL_LENGTH.getApplicationConstant() / 4) +
                                                StringUtils.rightPad(s,
                                                                (int) ApplicationConstants.BILL_LENGTH
                                                                                .getApplicationConstant() / 4,
                                                                padding)
                                                + t);
        }

        public static void printProducts(Collection<Product> products) {
                String productHeader = StringUtils.rightPad(" PRODUCT NAME ", 30, " ") +
                                StringUtils.rightPad("PRODUCT ID", 40, " ") +
                                StringUtils.rightPad("PRODUCT TYPE", 18, " ") +
                                StringUtils.rightPad("UNIT PRICE", 12, " ") +
                                StringUtils.rightPad("QUANTITY", 10, " ") +
                                StringUtils.rightPad("TOTAL PRICE", 12, " ");
                printBuffer();
                Utility.println(productHeader);
                printBuffer();
                products.stream().forEach(p -> {
                        BigDecimal quantity = new BigDecimal(p.getQuantity());
                        BigDecimal itemTotal = p.getUnitPrice().multiply(quantity);
                        String product = StringUtils.rightPad("* " + p.getName(), 30, " ") +
                                        StringUtils.rightPad(p.getId().toString(), 40, " ") +
                                        StringUtils.rightPad(p.getType().toString(), 18, " ") +
                                        StringUtils.rightPad("$" + p.getUnitPrice(), 12, " ") +
                                        StringUtils.rightPad(String.valueOf(p.getQuantity()), 10, " ") +
                                        StringUtils.rightPad("$" + itemTotal, 12, " ");
                        Utility.println(product);
                });

                BigDecimal total = products.stream()
                                .map(p -> p.getUnitPrice().multiply(new BigDecimal(p.getQuantity())))
                                .reduce(BigDecimal.ZERO, BigDecimal::add);
                printBuffer();
                printCenter("Undiscounted Bill = $" + ApplicationConstants.df.format(total),
                                ApplicationConstants.BILL_SPACE.getApplicationConstant().toString());
                printBuffer();
        }
}
