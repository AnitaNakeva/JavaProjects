package com.store;

import com.store.models.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReceiptTest {

    @Test
    void testTotalAmountCalculation() {
        Product p = new Product(1, "Water", 1.00, LocalDate.now().plusDays(10), ProductCategory.FOOD);
        ReceiptItem item = new ReceiptItem(p, 3);
        Cashier c = new Cashier(1, "Alex", 1000);
        Receipt receipt = new Receipt(1, c, List.of(item), 5, 10, 50, 100);
        assertEquals(4.5, receipt.getTotalAmount(), 0.01);
    }

    @Test
    void testReceiptSerialization() {
        Product p = new Product(1, "Soap", 2.00, LocalDate.now().plusDays(30), ProductCategory.NON_FOOD);
        ReceiptItem item = new ReceiptItem(p, 1);
        Cashier c = new Cashier(1, "Lara", 1500);
        Receipt receipt = new Receipt(2, c, List.of(item), 3, 10, 20, 30);
        receipt.saveToFile();

        assertTrue(new java.io.File("receipt_2.ser").exists());
    }

    @Test
    void testReceiptDeserialization() {
        Receipt receipt = Receipt.deserializeFromFile("receipt_2.ser");
        assertNotNull(receipt);
        assertEquals(2, receipt.getReceiptNumber());
    }
}
