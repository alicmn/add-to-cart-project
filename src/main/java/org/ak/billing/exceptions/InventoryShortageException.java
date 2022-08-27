package org.ak.billing.exceptions;

import org.ak.billing.constants.ApplicationConstants;

public class InventoryShortageException extends Exception{
    public InventoryShortageException() {
        super(ApplicationConstants.INVENTORY_SHORTAGE_EX_MSG.getApplicationConstant().toString());
    }

    public InventoryShortageException(String message) {
        super(message);
    }

    public InventoryShortageException(String message, Throwable cause) {
        super(message, cause);
    }

    public InventoryShortageException(Throwable cause) {
        super(cause);
    }

    public InventoryShortageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
