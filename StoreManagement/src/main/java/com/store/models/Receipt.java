package com.store.models;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;

public class Receipt implements Serializable {
    private static final long serialVersionUID = 1L;

    private int receiptNumber;
    private Cashier cashier;
    private LocalDateTime timestamp;
    private List<ReceiptItem> items;
    private double totalAmount;

    public Receipt(int receiptNumber, Cashier cashier, List<ReceiptItem> items,
                   int discountDays, double discountPercent,
                   double foodMarkup, double nonFoodMarkup) {
        setReceiptNumber(receiptNumber);
        setCashier(cashier);
        setTimestamp(LocalDateTime.now());
        setItems(items);
        setTotalAmount(calculateTotal(discountDays, discountPercent, foodMarkup, nonFoodMarkup));
    }

    private double calculateTotal(int discountDays, double discountPercent, double foodMarkup, double nonFoodMarkup) {
        double total = 0;
        for (ReceiptItem item : items) {
            double markup = item.getProduct().getCategory() == ProductCategory.FOOD ? foodMarkup : nonFoodMarkup;
            total += item.getTotalPrice(discountDays, discountPercent, timestamp, markup);
        }
        return Math.round(total * 100.0) / 100.0;
    }

    public int getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(int receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public Cashier getCashier() {
        return cashier;
    }

    public void setCashier(Cashier cashier) {
        this.cashier = cashier;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public List<ReceiptItem> getItems() {
        return items;
    }

    public void setItems(List<ReceiptItem> items) {
        this.items = items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void saveToFile() {
        String txtFile = "receipt_" + receiptNumber + ".txt";
        String serFile = "receipt_" + receiptNumber + ".ser";

        try (PrintWriter writer = new PrintWriter(new FileWriter(txtFile))) {
            writer.println("Receipt #" + receiptNumber);
            writer.println("Cashier: " + cashier.getName());
            writer.println("Date: " + timestamp);
            writer.println("Items:");
            for (ReceiptItem item : items) {
                writer.println(item.toString());
            }
            writer.println("Total: " + totalAmount + " BGN");
            System.out.println("Receipt saved to file: " + txtFile);
        } catch (IOException e) {
            System.err.println("Error saving receipt to text file: " + e.getMessage());
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(serFile))) {
            oos.writeObject(this);
            System.out.println("Receipt serialized to file: " + serFile);
        } catch (IOException e) {
            System.err.println("Error serializing receipt: " + e.getMessage());
        }
    }

    public static void readFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading receipt: " + e.getMessage());
        }
    }

    public static Receipt deserializeFromFile(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (Receipt) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error deserializing receipt: " + e.getMessage());
            return null;
        }
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "receiptNumber=" + receiptNumber +
                ", cashier=" + cashier.getName() +
                ", timestamp=" + timestamp +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
