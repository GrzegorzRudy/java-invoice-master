package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;

public abstract class Product {
    private final String name;

    private final BigDecimal price;

    private final BigDecimal taxPercent;

    protected Product(String name, BigDecimal price, BigDecimal tax) {
        if(name==null|name==""|price==null)throw new IllegalArgumentException("name or price must be not null, not empty");//this.name ="NONAME";
        if(price.compareTo(BigDecimal.ZERO) == -1)throw new IllegalArgumentException("price cannot be zero");
        else this.name = name;
        this.price = price;
        this.taxPercent = tax;
    }



    public String getName() {
         return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getTaxPercent() {
        return taxPercent;
    }

    public BigDecimal getPriceWithTax() {

        return (price.add(price.multiply(taxPercent)));
    }
}
