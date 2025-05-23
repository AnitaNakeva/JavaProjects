package com.store;

import com.store.models.Cashier;
import com.store.models.CheckoutRegister;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CheckoutRegisterTest {

    @Test
    void testSetCashier() {
        CheckoutRegister r = new CheckoutRegister(1);
        Cashier c = new Cashier(1, "Eli", 1000);
        r.setCashier(c);
        assertEquals(c, r.getCashier());
    }

    @Test
    void testGetRegisterNumber() {
        CheckoutRegister r = new CheckoutRegister(5);
        assertEquals(5, r.getRegisterNumber());
    }
}

