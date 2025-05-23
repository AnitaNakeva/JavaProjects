package com.store;

import com.store.models.Product;
import com.store.models.ProductCategory;
import com.store.models.ReceiptItem;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ReceiptItemTest {

    @Test
    void testGetTotalPriceWithMarkupAndNoDiscount() {
        Product product = new Product(1, "Juice", 3.0, LocalDate.now().plusMonths(2), ProductCategory.FOOD);
        ReceiptItem item = new ReceiptItem(product, 2);

        double result = item.getTotalPrice(5, 10, LocalDateTime.now(), 50);
        assertEquals(9.0, result, 0.001);
    }
}
