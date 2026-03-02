package org.ak.billing.daos.impls;

import org.ak.billing.beans.Product;
import org.ak.billing.constants.ProductTypes;
import org.ak.billing.daos.StoreDao;

import java.io.*;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

public class FileStoreDao implements StoreDao {
    private final String filePath;

    public FileStoreDao(String filePath) {
        this.filePath = filePath;
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
                // Add some default items to file
                FileWriter fw = new FileWriter(file);
                fw.write(UUID.randomUUID().toString() + ",BLUE MEN JACKET,20,CLOTHING,19.99\n");
                fw.write(UUID.randomUUID().toString() + ",ELIDOR SHAMPOO,62,COSMETICS,4.99\n");
                fw.write(UUID.randomUUID().toString() + ",MAC AIR PRO,120,ELECTRONICS,14.99\n");
                fw.write(UUID.randomUUID().toString() + ",SAMSUNG S3 MINI,20,PHONE,0.99\n");
                fw.write(UUID.randomUUID().toString() + ",CARTDORE Black Royal,45,STATIONERY,1.99\n");
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Set<Product> readFromFile() {
        Set<Product> products = new LinkedHashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty())
                    continue;
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    UUID id = UUID.fromString(parts[0]);
                    String name = parts[1];
                    int quantity = Integer.parseInt(parts[2]);
                    ProductTypes type = ProductTypes.valueOf(parts[3]);
                    BigDecimal unitPrice = new BigDecimal(parts[4]);
                    products.add(new Product(id, name, quantity, type, unitPrice));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }

    private void writeToFile(Set<Product> products) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Product p : products) {
                bw.write(p.getId().toString() + "," + p.getName() + "," + p.getQuantity() + "," + p.getType() + ","
                        + p.getUnitPrice() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean updateInventory(Product product) {
        Set<Product> allProducts = readFromFile();
        boolean found = false;

        // Product equals method compares ID
        Set<Product> updatedProducts = new LinkedHashSet<>();
        for (Product existing : allProducts) {
            if (existing.getId().equals(product.getId())) {
                updatedProducts.add(product);
                found = true;
            } else {
                updatedProducts.add(existing);
            }
        }

        if (!found) {
            updatedProducts.add(product);
        }

        writeToFile(updatedProducts);
        return true;
    }

    @Override
    public boolean updateInventoryBatch(Set<Product> products) {
        Set<Product> allProducts = readFromFile();

        Set<Product> updatedProducts = new LinkedHashSet<>();
        for (Product existing : allProducts) {
            Product replacement = null;
            for (Product p : products) {
                if (p.getId().equals(existing.getId())) {
                    replacement = p;
                    break;
                }
            }
            if (replacement != null) {
                updatedProducts.add(replacement);
            } else {
                updatedProducts.add(existing);
            }
        }

        writeToFile(updatedProducts);
        return true;
    }

    @Override
    public Product getProduct(UUID pid) {
        Set<Product> allProducts = readFromFile();
        return allProducts.stream().filter(p -> p.getId().equals(pid)).findFirst().orElse(null);
    }

    @Override
    public Set<Product> getAllProducts() {
        return readFromFile();
    }
}
