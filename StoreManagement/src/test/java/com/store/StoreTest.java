package com.store;

import com.store.models.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StoreTest {

    @Test
    void testDeliverProductAndInventorySize() {
        Store store = new Store("Test", 3, 20, 30, 40);
        Product product = new Product(1, "Apple", 1.0, LocalDate.now().plusDays(5), ProductCategory.FOOD);
        store.deliverProduct(product, 10);
        assertEquals(1, store.getInventory().size());
    }

    @Test
    void testSellProductAndReduceInventory() {
        Store store = new Store("Test", 3, 20, 30, 40);
        Product p = new Product(1, "Soda", 2.0, LocalDate.now().plusDays(3), ProductCategory.NON_FOOD);
        Cashier c = new Cashier(1, "Bob", 1000);
        CheckoutRegister r = new CheckoutRegister(1);
        c.assignRegister(r);
        store.addCashier(c);
        store.addRegister(r);
        store.deliverProduct(p, 5);

        ReceiptItem item = new ReceiptItem(p, 2);
        store.sellProducts(c, List.of(item));
        assertEquals(3, store.getInventory().get(p));
    }

    @Test
    void testSellExpiredProductThrows() {
        Store store = new Store("Test", 3, 20, 30, 40);
        Product p = new Product(1, "Cheese", 2.0, LocalDate.now().minusDays(1), ProductCategory.FOOD);
        Cashier c = new Cashier(1, "Eva", 1100);
        CheckoutRegister r = new CheckoutRegister(2);
        c.assignRegister(r);
        store.addCashier(c);
        store.addRegister(r);
        store.deliverProduct(p, 3);

        ReceiptItem item = new ReceiptItem(p, 1);

        Exception e = assertThrows(IllegalStateException.class, () ->
                store.sellProducts(c, List.of(item)));

        assertTrue(e.getMessage().contains("expired"));
    }
}
