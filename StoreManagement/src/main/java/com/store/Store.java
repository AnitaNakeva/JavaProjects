package com.store;

import com.store.models.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

public class Store implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private List<Cashier> cashiers;
    private List<CheckoutRegister> registers;
    private Map<Product, Integer> inventory;
    private Map<Product, Integer> soldProducts;
    private List<Receipt> receipts;
    private int discountDays;
    private double discountPercent;
    private double foodMarkupPercent;
    private double nonFoodMarkupPercent;
    private int totalReceiptsIssued;
    private double totalRevenue;

    public Store(String name, int discountDays, double discountPercent,
                 double foodMarkupPercent, double nonFoodMarkupPercent) {
        setName(name);
        setDiscountDays(discountDays);
        setDiscountPercent(discountPercent);
        setFoodMarkupPercent(foodMarkupPercent);
        setNonFoodMarkupPercent(nonFoodMarkupPercent);
        this.cashiers = new ArrayList<>();
        this.registers = new ArrayList<>();
        this.inventory = new HashMap<>();
        this.soldProducts = new HashMap<>();
        this.receipts = new ArrayList<>();
        this.totalReceiptsIssued = 0;
        this.totalRevenue = 0.0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Store name cannot be empty.");
        this.name = name;
    }

    public int getDiscountDays() {
        return discountDays;
    }

    public void setDiscountDays(int discountDays) {
        if (discountDays < 0) throw new IllegalArgumentException("Discount days cannot be negative.");
        this.discountDays = discountDays;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(double discountPercent) {
        if (discountPercent < 0 || discountPercent > 100)
            throw new IllegalArgumentException("Discount percent must be between 0 and 100.");
        this.discountPercent = discountPercent;
    }

    public double getFoodMarkupPercent() {
        return foodMarkupPercent;
    }

    public void setFoodMarkupPercent(double foodMarkupPercent) {
        if (foodMarkupPercent < 0) throw new IllegalArgumentException("Food markup cannot be negative.");
        this.foodMarkupPercent = foodMarkupPercent;
    }

    public double getNonFoodMarkupPercent() {
        return nonFoodMarkupPercent;
    }

    public void setNonFoodMarkupPercent(double nonFoodMarkupPercent) {
        if (nonFoodMarkupPercent < 0) throw new IllegalArgumentException("Non-food markup cannot be negative.");
        this.nonFoodMarkupPercent = nonFoodMarkupPercent;
    }

    public int getTotalReceiptsIssued() {
        return totalReceiptsIssued;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public List<Receipt> getReceipts() {
        return receipts;
    }

    public Map<Product, Integer> getInventory() {
        return inventory;
    }

    public Map<Product, Integer> getSoldProducts() {
        return soldProducts;
    }

    public void addCashier(Cashier cashier) {
        if (cashier == null) throw new IllegalArgumentException("Cashier cannot be null.");
        cashiers.add(cashier);
    }

    public void addRegister(CheckoutRegister register) {
        if (register == null) throw new IllegalArgumentException("Register cannot be null.");
        registers.add(register);
    }

    public void deliverProduct(Product product, int quantity) {
        if (product == null) throw new IllegalArgumentException("Product cannot be null.");
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive.");
        inventory.put(product, inventory.getOrDefault(product, 0) + quantity);
    }

    public void sellProducts(Cashier cashier, List<ReceiptItem> items) {
        if (cashier == null || items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Invalid cashier or items.");
        }

        CheckoutRegister register = cashier.getRegister();
        if (register == null) {
            throw new IllegalStateException("Cashier " + cashier.getName() + " is not assigned to a register.");
        }

        if (!registers.contains(register)) {
            throw new IllegalStateException("Register " + register.getRegisterNumber() + " does not belong to this store.");
        }

        LocalDate today = LocalDate.now();

        for (ReceiptItem item : items) {
            Product product = item.getProduct();
            int requested = item.getQuantity();
            int available = inventory.getOrDefault(product, 0);

            if (product.isExpired(today)) {
                throw new IllegalStateException("Product expired: " + product.getName());
            }

            if (requested > available) {
                int needed = requested - available;
                throw new IllegalStateException("Not enough stock for: " + product.getName() + ". Needed " + needed + " more");
            }
        }

        for (ReceiptItem item : items) {
            Product product = item.getProduct();
            int quantity = item.getQuantity();

            inventory.put(product, inventory.get(product) - quantity);
            soldProducts.put(product, soldProducts.getOrDefault(product, 0) + quantity);
        }

        totalReceiptsIssued++;
        Receipt receipt = new Receipt(
                totalReceiptsIssued,
                cashier,
                items,
                discountDays,
                discountPercent,
                foodMarkupPercent,
                nonFoodMarkupPercent
        );

        receipts.add(receipt);
        totalRevenue += receipt.getTotalAmount();
        receipt.saveToFile();
    }

    public double calculateTotalSalaries() {
        double total = 0;
        for (Cashier cashier : cashiers) {
            total += cashier.getSalary();
        }
        return total;
    }

    public double calculateSupplyCost() {
        double total = 0;
        for (Product product : inventory.keySet()) {
            int quantity = inventory.get(product);
            double price = product.getPurchasePrice();
            total += price * quantity;
        }
        return total;
    }

    public double calculateProfit() {
        return totalRevenue - (calculateTotalSalaries() + calculateSupplyCost());
    }

    public void printStoreStatistics() {
        System.out.println("Store: " + name);
        System.out.println("Cashiers: " + cashiers.size());
        System.out.println("Registers: " + registers.size());
        System.out.println("Inventory items: " + inventory.size());
        System.out.println("Sold items: " + soldProducts.size());
        System.out.println("Receipts issued: " + totalReceiptsIssued);
        System.out.println("Total revenue: " + Math.round(totalRevenue * 100.0) / 100.0 + " BGN");
        System.out.println("Expenses: " + Math.round((calculateTotalSalaries() + calculateSupplyCost()) * 100.0) / 100.0 + " BGN");
        System.out.println("Profit: " + Math.round(calculateProfit() * 100.0) / 100.0 + " BGN");
    }

    public void printSoldProducts() {
        System.out.println("Sold products:");
        for (Product product : soldProducts.keySet()) {
            int quantity = soldProducts.get(product);
            System.out.println("- " + product.getName() + ": " + quantity + " pcs");
        }
    }
}
