package org.ak.billing;

import org.ak.billing.beans.Product;
import org.ak.billing.beans.Shopper;
import org.ak.billing.beans.ShoppingCart;
import org.ak.billing.beans.UserDetails;
import org.ak.billing.constants.UserTypes;
import org.ak.billing.daos.impls.MyStoreDao;
import org.ak.billing.services.InvoiceService;
import org.ak.billing.services.StoreDBService;
import org.ak.billing.services.impls.MyCartService;
import org.ak.billing.services.impls.MyInvoiceService;
import org.ak.billing.services.impls.MyStoreDBService;
import org.ak.billing.strategies.impls.MyCartLoadingStrategy;
import org.ak.billing.strategies.impls.MyInvoiceGenerator;
import org.ak.billing.strategies.impls.Store;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            // Servislerin başlatılması (Dependency Injection kurulumu)
            StoreDBService myStoreDBService = new MyStoreDBService(new MyStoreDao(Store.getStore()));
            MyCartService myCartService = new MyCartService(myStoreDBService, new MyCartLoadingStrategy());
            InvoiceService myInvoiceService = new MyInvoiceService(new MyInvoiceGenerator());

            System.out.println("E-Ticaret Sistemimize Hosgeldiniz!");
            System.out.println("Lutfen Musteri Tipinizi Secin:");
            System.out.println("1 - Sirket Calisani (AFFILIATE)");
            System.out.println("2 - Gold Uye (GOLD_CART)");
            System.out.println("3 - Silver Uye (SILVER_CART)");
            System.out.println("4 - Eski Musteri (>2 Yil Uye)");
            System.out.println("5 - Yeni Musteri (Standart/Ziyaretci)");
            System.out.print("Seciminiz: ");

            int userChoice = Integer.parseInt(scanner.nextLine());
            UserTypes type = UserTypes.CUSTOMER;
            LocalDateTime userSince = LocalDateTime.now();

            switch (userChoice) {
                case 1:
                    type = UserTypes.AFFILIATE;
                    break;
                case 2:
                    type = UserTypes.GOLD_CART;
                    break;
                case 3:
                    type = UserTypes.SILVER_CART;
                    break;
                case 4:
                    type = UserTypes.CUSTOMER;
                    userSince = LocalDateTime.now().minusYears(3);
                    break;
                case 5:
                    type = UserTypes.CUSTOMER;
                    break;
                default:
                    System.out.println("Gecersiz secim, varsayilan olarak Yeni Musteri atandı.");
            }

            UserDetails userDetails = new UserDetails.Builder()
                    .uid("Musteri_ID_001")
                    .userType(type)
                    .userSince(userSince)
                    .contacts("+90-555-555-55", "test@example.com")
                    .build();
            ShoppingCart cart = myCartService.getNewShoppingCart();
            Shopper shopper = new Shopper(userDetails, cart);

            Set<Product> inventory = myStoreDBService.getInventory();
            List<Product> productList = new ArrayList<>(inventory);

            while (true) {
                System.out.println("\n------------- URUN LISTEMIZ -------------");
                for (int i = 0; i < productList.size(); i++) {
                    Product p = productList.get(i);
                    System.out.println((i + 1) + ". " + p.getName() + " | Tip: " + p.getType() + " | Fiyat: $"
                            + p.getUnitPrice() + " | Stok: " + p.getQuantity());
                }
                System.out.println("0. Alisverisi Tamamla (Kasaya Git)");

                System.out.print("\nSepete eklemek istediginiz urun numarasini secin: ");
                int prodChoice;
                try {
                    prodChoice = Integer.parseInt(scanner.nextLine());
                } catch (Exception e) {
                    System.out.println("Hata: Lutfen gecerli bir sayi girin.");
                    continue;
                }

                if (prodChoice == 0) {
                    break;
                }

                if (prodChoice < 1 || prodChoice > productList.size()) {
                    System.out.println("Hata: Gecersiz urun numarasi.");
                    continue;
                }

                Product selectedProduct = productList.get(prodChoice - 1);

                System.out.print("Kac adet " + selectedProduct.getName() + " eklemek istiyorsunuz?: ");
                int quantity;
                try {
                    quantity = Integer.parseInt(scanner.nextLine());
                } catch (Exception e) {
                    System.out.println("Hata: Gecerli bir miktar girin.");
                    continue;
                }

                if (quantity <= 0) {
                    System.out.println("Hata: Gecersiz miktar.");
                    continue;
                }

                if (quantity > selectedProduct.getQuantity()) {
                    System.out.println("HATA: Depoda o urunden yeterli stok bulunmuyor! (Mevcut: "
                            + selectedProduct.getQuantity() + ")");
                    continue;
                }

                try {
                    Product cloneForCart = (Product) selectedProduct.clone();
                    cloneForCart.setQuantity(quantity);
                    myCartService.addProduct(cloneForCart, cart);
                    System.out.println(
                            "==> [" + selectedProduct.getName() + "] sepetinize " + quantity + " adet eklendi!");
                } catch (CloneNotSupportedException e) {
                    System.out.println("Klonlama hatasi.");
                }
            }

            if (myCartService.getAllProducts(cart).isEmpty()) {
                System.out.println("\nSepetiniz bos. Alisveris sonlandirildi.");
                return;
            }

            System.out.println("\nKasaya yonlendiriliyorsunuz...");
            // Kasada stokları gercekten depodan yansit / dus
            myStoreDBService.updateInventory(myCartService.getAllProducts(cart));

            // Fatura uretimi ve ekrana basilmasi
            myInvoiceService.generate(shopper);
            myInvoiceService.print(shopper);

            BigDecimal totalBill = shopper.getInvoice().getAmount();
            DecimalFormat df = new DecimalFormat("#.##");
            System.out.println("\n==================================");
            System.out.println("NET ODENECEK TUTAR: $" + df.format(totalBill));
            System.out.println("Bizi tercih ettiginiz icin tesekkurler!");
            System.out.println("==================================");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
