package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Queue;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    private ArrayList<Product> products;

    protected Invoice(){
    this.products = new ArrayList<Product>();
    }

    public void addProduct(Product product) {
        if(product==null)throw new IllegalArgumentException("quantity must be not null, not empty");
        products.add(product);
    }

    public void addProduct(Product product, Integer quantity) {
        if(product.getName()==null|product.getName()==""|quantity==null|quantity<=0)throw new IllegalArgumentException("quantity must be not null, not empty");
        for(int i=0;i<quantity;i++){
            products.add(product);
        }
    }

    public BigDecimal getSubtotal() {
        BigDecimal sum= new BigDecimal("0");
        for(Product next: products){
           sum = sum.add(next.getPrice());
        }
        if(products.isEmpty())return BigDecimal.ZERO;
        return sum;


    }

    public BigDecimal getTax() {
        BigDecimal sum= new BigDecimal("0");
        for(Product next: products){
            sum = sum.add(next.getPrice().multiply(next.getTaxPercent()));
        }
        if(products.isEmpty())return BigDecimal.ZERO;
        return sum;
    }

    public BigDecimal getTotal() {
        return getSubtotal().add(getTax());
    }
}
