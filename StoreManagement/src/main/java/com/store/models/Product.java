package com.store.models;

import java.io.Serializable;
import java.time.LocalDate;

public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private double purchasePrice;
    private LocalDate expirationDate;
    private ProductCategory category;

    public Product(int id, String name, double purchasePrice, LocalDate expirationDate, ProductCategory category) {
        setId(id);
        setName(name);
        setPurchasePrice(purchasePrice);
        setExpirationDate(expirationDate);
        setCategory(category);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id <= 0) throw new IllegalArgumentException("ID must be positive.");
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Product name cannot be empty.");
        this.name = name;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        if (purchasePrice < 0) throw new IllegalArgumentException("Purchase price cannot be negative.");
        this.purchasePrice = purchasePrice;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        if (expirationDate == null) throw new IllegalArgumentException("Expiration date cannot be null.");
        this.expirationDate = expirationDate;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        if (category == null) throw new IllegalArgumentException("Category cannot be null.");
        this.category = category;
    }

    public boolean isExpired(LocalDate currentDate) {
        return expirationDate.isBefore(currentDate);
    }

    public double getSellingPrice(LocalDate currentDate, int discountDays, double discountPercent, double markupPercent) {
        double price = purchasePrice * (1 + markupPercent / 100);

        if (expirationDate.minusDays(discountDays).isBefore(currentDate)) {
            price *= (1 - discountPercent / 100);
        }

        return Math.round(price * 100.0) / 100.0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Product)) return false;
        Product other = (Product) obj;
        return this.id == other.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
