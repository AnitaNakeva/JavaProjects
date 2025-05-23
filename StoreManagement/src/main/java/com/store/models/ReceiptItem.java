package com.store.models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ReceiptItem implements Serializable {
    private static final long serialVersionUID = 1L;

    private Product product;
    private int quantity;

    public ReceiptItem(Product product, int quantity) {
        setProduct(product);
        setQuantity(quantity);
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        if (product == null) throw new IllegalArgumentException("Product cannot be null.");
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be at least 1.");
        this.quantity = quantity;
    }

    public double getTotalPrice(int discountDays, double discountPercent, LocalDateTime dateTime, double markupPercent) {
        double unitPrice = product.getSellingPrice(dateTime.toLocalDate(), discountDays, discountPercent, markupPercent);
        return Math.round(unitPrice * quantity * 100.0) / 100.0;
    }

    @Override
    public String toString() {
        return product.getName() + " - " + quantity + " x " + product.getPurchasePrice() + " BGN";
    }
}
