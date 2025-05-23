package com.store.models;

import java.io.Serializable;

public class CheckoutRegister implements Serializable {
    private static final long serialVersionUID = 1L;

    private int registerNumber;
    private Cashier cashier;

    public CheckoutRegister(int registerNumber) {
        setRegisterNumber(registerNumber);
        this.cashier = null;
    }

    public void setRegisterNumber(int registerNumber) {
        if (registerNumber <= 0) throw new IllegalArgumentException("Register number must be a positive number.");
        this.registerNumber = registerNumber;
    }

    public int getRegisterNumber() {
        return registerNumber;
    }

    public Cashier getCashier() {
        return cashier;
    }

    public void setCashier(Cashier cashier) {
        this.cashier = cashier;
    }

    @Override
    public String toString() {
        return "CheckoutRegister{" +
                "registerNumber=" + registerNumber +
                ", cashier=" + (cashier != null ? cashier.getName() : "No cashier assigned") +
                '}';
    }
}
