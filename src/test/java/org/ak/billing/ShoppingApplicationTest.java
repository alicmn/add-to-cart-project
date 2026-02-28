package org.ak.billing;

import org.ak.billing.beans.UserDetails;
import org.ak.billing.constants.UserTypes;
import org.ak.billing.daos.impls.MyStoreDao;
import org.ak.billing.exceptions.InventoryShortageException;
import org.ak.billing.services.InvoiceService;
import org.ak.billing.services.StoreDBService;
import org.ak.billing.services.impls.MyCartService;
import org.ak.billing.services.impls.MyInvoiceService;
import org.ak.billing.services.impls.MyStoreDBService;
import org.ak.billing.strategies.impls.MyCartLoadingStrategy;
import org.ak.billing.strategies.impls.MyInvoiceGenerator;
import org.ak.billing.strategies.impls.Store;
import org.junit.Before;
import org.junit.Test;

import java.text.DecimalFormat;
import java.time.LocalDateTime;

import static junit.framework.TestCase.assertEquals;

public class ShoppingApplicationTest {

    private StoreDBService myStoreDBService;
    private MyCartService myCartService;
    private InvoiceService myInvoiceService;
    private DecimalFormat df;

    @Before
    public void setUp() throws Exception {
        // Services
        myStoreDBService = new MyStoreDBService(new MyStoreDao(Store.getStore()));
        myCartService = new MyCartService(myStoreDBService, new MyCartLoadingStrategy());
        myInvoiceService = new MyInvoiceService(new MyInvoiceGenerator());
        df = new DecimalFormat("#.####");
    }

    @Test
    public void UserIsAffiliate() throws InventoryShortageException {
        ShoppingApplication shoppingApplication = new ShoppingApplication(myStoreDBService,
                myCartService, myInvoiceService);
        LocalDateTime localDateTime = LocalDateTime.of(2018, 11, 22, 3, 15);
        UserDetails userDetails = new UserDetails("Ali Cimen", UserTypes.AFFILIATE, localDateTime, "+90-535-000-43",
                "ali@gmail.com");
        assertEquals("Bill Amount must be 75.6198 for this User", df.format(75.6198d),
                df.format(shoppingApplication.shop(userDetails)));
    }

    @Test
    public void UserHasGoldCart() throws InventoryShortageException {
        ShoppingApplication shoppingApplication = new ShoppingApplication(myStoreDBService,
                myCartService, myInvoiceService);
        LocalDateTime localDateTime = LocalDateTime.of(2018, 11, 22, 3, 15);
        UserDetails userDetails = new UserDetails("Umut Cahan", UserTypes.GOLD_CART, localDateTime, "+90-565-000-01",
                "umut@gmail.com");
        assertEquals("Bill Amount must be 59.2554 for this User", df.format(59.2554d),
                df.format(shoppingApplication.shop(userDetails)));
    }

    @Test
    public void UserHasSılverCart() throws InventoryShortageException {
        ShoppingApplication shoppingApplication = new ShoppingApplication(myStoreDBService,
                myCartService, myInvoiceService);
        LocalDateTime localDateTime = LocalDateTime.of(2018, 11, 22, 3, 15);
        UserDetails userDetails = new UserDetails("Ali Filiz", UserTypes.SILVER_CART, localDateTime, "+90-532-000-01",
                "ali@gmail.com");
        assertEquals("Bill Amount must be 67.4376 for this User", df.format(67.4376d),
                df.format(shoppingApplication.shop(userDetails)));
    }

    @Test
    public void UserIsRecentCustomer() throws InventoryShortageException {
        ShoppingApplication shoppingApplication = new ShoppingApplication(myStoreDBService,
                myCartService, myInvoiceService);
        LocalDateTime localDateTime = LocalDateTime.of(2019, 11, 22, 3, 15);
        UserDetails userDetails = new UserDetails("Ahmet Can", UserTypes.CUSTOMER, localDateTime, "+90-565-000-41",
                "ahmet@gmail.com");
        assertEquals("Bill Amount must be 83.802 for this User", df.format(83.802d),
                df.format(shoppingApplication.shop(userDetails)));
    }

    @Test
    public void UserIsOldCustomer() throws InventoryShortageException {
        ShoppingApplication shoppingApplication = new ShoppingApplication(myStoreDBService,
                myCartService, myInvoiceService);
        LocalDateTime localDateTime = LocalDateTime.of(2015, 11, 22, 3, 15);
        UserDetails userDetails = new UserDetails("Mehmet Cetinkaya", UserTypes.CUSTOMER, localDateTime,
                "+90-565-000-001", "mehmet@gmail.com");
        assertEquals("Bill Amount must be 79.7109 for this User", df.format(79.7109d),
                df.format(shoppingApplication.shop(userDetails)));
    }
}
