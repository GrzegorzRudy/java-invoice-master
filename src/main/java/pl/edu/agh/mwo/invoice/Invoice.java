package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    private Map<Product, Integer> products = new HashMap<Product, Integer>();

    public void addProduct(Product product) {
        addProduct(product, 1);
    }

    public void addProduct(Product product, Integer quantity) {
        if (product == null || quantity <= 0) {
            throw new IllegalArgumentException();
        }
        //products.put(product, quantity);

        boolean isDuplicate = false;
        for (Product existingProduct : products.keySet()) {
            if (existingProduct.getName().equals(product.getName())) {
                // Jesli produkt występuję, zwiększ liczbę
                Integer QuantityBefore = products.get(existingProduct); //odczytaj ile było
                products.put(existingProduct, QuantityBefore + quantity);//zwieksz
                isDuplicate = true;
                break; //wyjdz z petli
            }
        }

        // Jeśli nie ma takiej pozycji, dodaj nową pozycję na fakturze
        if (!isDuplicate) {
            products.put(product, quantity);
       }

    }

    public BigDecimal getNetTotal() {
        BigDecimal totalNet = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalNet = totalNet.add(product.getPrice().multiply(quantity));
        }
        return totalNet;
    }

    public BigDecimal getTaxTotal() {
        return getGrossTotal().subtract(getNetTotal());
    }

    public BigDecimal getGrossTotal() {
        BigDecimal totalGross = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalGross = totalGross.add(product.getPriceWithTax().multiply(quantity));
        }
        return totalGross;
    }
    public int getNumber() {
        return new Random().nextInt(9999);
    }
    public int getProductQuantity(Product product) {
        return products.getOrDefault(product, 0);
    }
    public int getNumberOfPosition() {
        return products.size();
    }


}
