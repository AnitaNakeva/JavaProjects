package com.store;

import com.store.models.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        /*
        Store store = new Store("Billa",
                3,
                20.0,
                30.0,
                50.0
        );

        Cashier cashier = new Cashier(1, "Ivan", 1200);
        CheckoutRegister register = new CheckoutRegister(1);
        cashier.assignRegister(register);

        store.addCashier(cashier);
        store.addRegister(register);

        Product milk = new Product(1, "Fresh Milk", 1.00, LocalDate.now().plusDays(2), ProductCategory.FOOD);
        Product shampoo = new Product(2, "Shampoo", 3.00, LocalDate.now().plusMonths(2), ProductCategory.NON_FOOD);

        store.deliverProduct(milk, 10);
        store.deliverProduct(shampoo, 5);

        ReceiptItem item1 = new ReceiptItem(milk, 2);
        ReceiptItem item2 = new ReceiptItem(shampoo, 1);

        List<ReceiptItem> items = Arrays.asList(item1, item2);

        try {
            store.sellProducts(cashier, items);
        } catch (Exception e) {
            System.out.println("Error during sale: " + e.getMessage());
        }

        store.printStoreStatistics();

        for (Receipt receipt : store.getReceipts()) {
            String fileName = "receipt_" + receipt.getReceiptNumber() + ".txt";
            System.out.println("\n--- Reading " + fileName + " ---");
            Receipt.readFromFile(fileName);
        }*/

        //store 2

        Store store2 = new Store("Lidl", 5, 20, 25, 30);

        Cashier cashier2 = new Cashier(2, "Sofia", 1800);
        Cashier cashier3 = new Cashier(3, "Petar", 2300);

        CheckoutRegister register2 = new CheckoutRegister(2);
        CheckoutRegister register3 = new CheckoutRegister(3);
        CheckoutRegister register4 = new CheckoutRegister(4);

        cashier3.assignRegister(register4);

        store2.addCashier(cashier2);
        store2.addCashier(cashier3);
        store2.addRegister(register2);
        store2.addRegister(register3);
        store2.addRegister(register4);

        Product chocolate = new Product(3, "Chocolate", 5.00, LocalDate.now().plusMonths(11), ProductCategory.FOOD);
        Product banichka = new Product(4, "Banichka", 0.80, LocalDate.now().plusDays(1), ProductCategory.FOOD);
        Product toothPaste = new Product(5, "Tooth Paste", 5.60, LocalDate.now().plusMonths(12), ProductCategory.NON_FOOD);

        store2.deliverProduct(chocolate, 50);
        store2.deliverProduct(banichka, 60);
        store2.deliverProduct(toothPaste, 35);

        ReceiptItem item3 = new ReceiptItem(chocolate, 2);
        ReceiptItem item4 = new ReceiptItem(toothPaste, 1);

        List<ReceiptItem> items2 = Arrays.asList(item3, item4);

        ReceiptItem item5 = new ReceiptItem(chocolate, 1);
        ReceiptItem item6 = new ReceiptItem(banichka, 12);

        List<ReceiptItem> items3 = Arrays.asList(item5, item6);

        try {
            store2.sellProducts(cashier3, items2);
        } catch (Exception e) {
            System.out.println("Error during sale: " + e.getMessage());
        }

        try {
            store2.sellProducts(cashier2, items3);
        } catch (Exception e) {
            System.out.println("Error during sale: " + e.getMessage());
        }

        try {
            store2.sellProducts(cashier3, items3);
        } catch (Exception e) {
            System.out.println("Error during sale: " + e.getMessage());
        }

        store2.printStoreStatistics();

        for (Receipt receipt : store2.getReceipts()) {
            String fileName = "receipt_" + receipt.getReceiptNumber() + ".txt";
            System.out.println("\n--- Reading " + fileName + " ---");
            Receipt.readFromFile(fileName);
        }

        Receipt deserialized = Receipt.deserializeFromFile("receipt_1.ser");

        if (deserialized != null) {
            System.out.println();
            System.out.println("Deserialized receipt:");
            System.out.println(deserialized);
        } else {
            System.out.println("Failed to deserialize receipt.");
        }

    }
}
