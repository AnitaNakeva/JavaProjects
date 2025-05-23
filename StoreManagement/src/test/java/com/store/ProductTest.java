package com.store;

import com.store.models.Product;
import com.store.models.ProductCategory;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    @Test
    void testIsExpired_returnsFalse() {
        Product product = new Product(1, "Milk", 2.0, LocalDate.now().plusDays(3), ProductCategory.FOOD);
        assertFalse(product.isExpired(LocalDate.now()));
    }

    @Test
    void testIsExpired_returnsTrue() {
        Product product = new Product(1, "Milk", 2.0, LocalDate.now().minusDays(1), ProductCategory.FOOD);
        assertTrue(product.isExpired(LocalDate.now()));
    }

    @Test
    void testNameCannotBeEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
                new Product(1, "", 2.0, LocalDate.now().plusDays(1), ProductCategory.FOOD));
    }

    @Test
    void testPurchasePriceCannotBeNegative() {
        assertThrows(IllegalArgumentException.class, () ->
                new Product(1, "Water", -5.0, LocalDate.now().plusDays(10), ProductCategory.FOOD));
    }
}
