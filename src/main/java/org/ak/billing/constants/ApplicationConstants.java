package org.ak.billing.constants;

import java.text.DecimalFormat;

public enum ApplicationConstants {
    CART_QUANTITY(2),
    SHOW_LOGS(true),
    INVENTORY_SHORTAGE_EX_MSG("**Cannot add from inventory, product is in shortage! **Please restock inventory**"),
    DATE_TIME_FORMAT("cccc dd-MMMM-uuuu hh:mm a"),
    BILL_HEADER(" MY RETAIL STORE BILL "),
    BILL_PRODUCT_HEADER(" PRODUCTS PURCHASED "),
    BILL_LENGTH(122),
    BILL_PADDING("*"),
    BILL_SPACE(" ");

    private final Object appCons;

    public static final DecimalFormat df = new DecimalFormat("#.##");

    ApplicationConstants(Object appCons){
        this.appCons = appCons;
    }

    public final Object getApplicationConstant(){
        return appCons;
    }
}
