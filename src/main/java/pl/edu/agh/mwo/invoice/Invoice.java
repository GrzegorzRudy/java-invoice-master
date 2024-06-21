package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import pl.edu.agh.mwo.invoice.product.*;

public class Invoice {
    public static void main(String[] args){

        Invoice invoice = new Invoice();
        Product product = new DairyProduct("Mleko", new BigDecimal("4.50"));
        Product product2 = new OtherProduct("szklanka", new BigDecimal("4.50"));
        Product product3 = new FuelCanister("ropa", new BigDecimal("4.50"));
        Product product4 = new BottleOfWine("Amarena", new BigDecimal("16.50"));
        invoice.addProduct(product, 2);
        invoice.addProduct(product, 3);
        invoice.addProduct(product, 5);
        invoice.addProduct(product2, 5);
        invoice.addProduct(product3, 1);
        invoice.addProduct(product4, 1);
        System.out.println(invoice.generateProductList());
    }
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
    public String generateProductList() {

        StringBuilder productList = new StringBuilder();

        productList.append("Numer faktury: ");
        productList.append(getNumber());
        productList.append("\n");

        productList.append("---------------------------------------------------------------\n");
        productList.append("|                       Lista produktów                       |\n");
        productList.append("---------------------------------------------------------------\n");

        products.forEach((product, quantity) -> {
            BigDecimal totalPrice = product.getPrice().multiply(BigDecimal.valueOf(quantity));
            String productName = product.getName();
            String quantityAndPrice = "Ilość:" + quantity + "|Cena:" + totalPrice;
            String line = String.format("| %-30s | %-10s |\n", productName, quantityAndPrice);
            productList.append(line);
        });

        productList.append("---------------------------------------------------------------\n");
        productList.append("Liczba pozycji: ");
        productList.append(getNumberOfItems());

        return productList.toString();
    }
    public int getNumberOfItems() {return products.values().stream().mapToInt(Integer::intValue).sum();
    }

}
