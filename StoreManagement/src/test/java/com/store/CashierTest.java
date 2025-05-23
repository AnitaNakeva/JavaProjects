package com.store;

import com.store.models.Cashier;
import com.store.models.CheckoutRegister;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CashierTest {

    @Test
    void testAssignRegister() {
        Cashier c = new Cashier(1, "Maria", 1200);
        CheckoutRegister r = new CheckoutRegister(1);
        c.assignRegister(r);
        assertEquals(r, c.getRegister());
        assertEquals(c, r.getCashier());
    }

    @Test
    void testUnassignRegister() {
        Cashier c = new Cashier(2, "Tom", 1100);
        CheckoutRegister r = new CheckoutRegister(2);
        c.assignRegister(r);
        c.unassignRegister();
        assertNull(c.getRegister());
        assertNull(r.getCashier());
    }
}
